USE sqldados;

DO @LOJA := :loja;
DO @VENDNO := IF(:pesquisa REGEXP '^[0-9]+$', :pesquisa * 1, 0);
DO @FORNECEDOR := :pesquisa;

DROP TABLE IF EXISTS T_BARCODE;
CREATE TEMPORARY TABLE T_BARCODE (
  PRIMARY KEY (prdno, grade)
)
SELECT prdno,
       grade,
       TRIM(barcode) AS barcode
FROM sqldados.prdbar
GROUP BY prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_REF;
CREATE TEMPORARY TABLE T_REF (
  PRIMARY KEY (prdno, grade)
)
SELECT prdno                                                                  AS prdno,
       grade                                                                  AS grade,
       CAST(MID(MAX(CONCAT(LPAD(l1, 10, '0'), prdrefno)), 11, 100) AS char)   AS refno,
       CAST(MID(MAX(CONCAT(LPAD(l1, 10, '0'), prdrefname)), 11, 100) AS char) AS refname
FROM sqldados.prdref
GROUP BY prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_REF_SEMGRADE;
CREATE TEMPORARY TABLE T_REF_SEMGRADE (
  PRIMARY KEY (prdno)
)
SELECT prdno                                                                  AS prdno,
       CAST(MID(MAX(CONCAT(LPAD(l1, 10, '0'), prdrefno)), 11, 100) AS char)   AS refno,
       CAST(MID(MAX(CONCAT(LPAD(l1, 10, '0'), prdrefname)), 11, 100) AS char) AS refname
FROM sqldados.prdref
WHERE grade = ''
GROUP BY prdno;

SELECT V.no                                                                         AS vendno,
       V.name                                                                       AS fornecedor,
       V.cgc                                                                        AS cnpj,
       O.storeno                                                                    AS loja,
       S.sname                                                                      AS sigla,
       O.no                                                                         AS numeroPedido,
       O.status                                                                     AS status,/*0 - Aberto,  1 - Entregue, 2 - Cancelado*/
       CAST(O.date AS DATE)                                                         AS dataPedido,
       CAST(ROUND(IF(O.dataEntrega = 0, DATE_ADD(O.date, INTERVAL O.deliv DAY) * 1,
		     O.dataEntrega)) AS DATE)                                       AS dataEntrega,
       O.remarks                                                                    AS obsercacaoPedido,
       TRIM(E.prdno)                                                                AS codigo,
       TRIM(MID(P.name, 1, 37))                                                     AS descricao,
       P.mfno_ref                                                                   AS refFab,
       E.grade                                                                      AS grade,
       MID(P.name, 38, 3)                                                           AS unidade,
       COALESCE(R.refno, RS.refno, '')                                              AS refno,
       COALESCE(R.refname, RS.refname, '')                                          AS refname,
       ROUND((E.qtty * E.mult) / 1000)                                              AS qtPedida,
       ROUND((E.qttyCancel * E.mult) / 1000)                                        AS qtCancelada,
       ROUND((E.qttyRcv * E.mult) / 1000)                                           AS qtRecebida,
       ROUND((E.qtty * E.mult - E.qttyCancel * E.mult - E.qttyRcv * E.mult) / 1000) AS qtPendente,
       E.cost                                                                       AS custoUnit,
       TRIM(IFNULL(B.barcode, P.barcode))                                           AS barcode
FROM sqldados.ords          AS O
  INNER JOIN sqldados.store AS S
	       ON S.no = O.storeno
  INNER JOIN sqldados.vend  AS V
	       ON O.vendno = V.no
  INNER JOIN sqldados.oprd  AS E
	       ON O.storeno = E.storeno AND O.no = E.ordno
  LEFT JOIN  T_REF          AS R
	       ON E.prdno = R.prdno AND E.grade = R.grade
  LEFT JOIN  T_REF_SEMGRADE AS RS
	       ON E.prdno = RS.prdno
  LEFT JOIN  T_BARCODE      AS B
	       ON E.prdno = B.prdno AND E.grade = B.grade
  INNER JOIN sqldados.prd   AS P
	       ON P.no = E.prdno
WHERE (O.storeno = :loja OR :loja = 0)
  AND (V.no = @VENDNO OR @VENDNO = 0)
  AND (V.name LIKE CONCAT(@FORNECEDOR, '%'))
  AND O.status != 2
  AND O.date >= SUBDATE(CURRENT_DATE, INTERVAL 6 MONTH)

