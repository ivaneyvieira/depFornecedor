DO @storeno := :storeno;
DO @di := :di;
DO @df := :df;
DO @vendno := :vendno;
DO @ni := :ni;
DO @nf := :nf;
DO @prd := LPAD(:prd, 16, ' ');
DO @cst := :cst;
DO @icms := :icms;
DO @ipi := :ipi;
DO @mva := :mva;
DO @ncm := :ncm;
DO @barcode := :barcode;
DO @refPrd := :refPrd;

SELECT COUNT(*)
FROM sqldados.query1234567
WHERE @cst = cstDif
   OR @icms = icmsDif
   OR @ipi = ipiDif
   OR @mva = mvaDif
   OR @ncm = ncmDif
   OR @barcode = barcodeDif
   OR @refPrd = refPrdDif
   OR (@cst = 'T' AND @icms = 'T' AND @ipi = 'T' AND @mva = 'T' AND @ncm = 'T' AND
       @barcode = 'T' AND @refPrd = 'T')

