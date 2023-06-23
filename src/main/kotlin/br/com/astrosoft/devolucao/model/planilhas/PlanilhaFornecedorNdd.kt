package br.com.astrosoft.devolucao.model.planilhas

import br.com.astrosoft.devolucao.model.beans.FornecedorNdd
import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import com.github.nwillc.poink.workbook
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import java.io.ByteArrayOutputStream

class PlanilhaFornecedorNdd {
  private val campos: List<Campo<*, FornecedorNdd>> =
      listOf(
          CampoInt("Código Saci") { vendno },
          CampoString("Fornecedor") { nome },
          CampoString("Primeira Data") { primeiraDataStr },
          CampoString("Ultima Data") { ultimaDataStr },
          CampoNumber("Saldo") { valorTotal },
      )

  fun grava(listaNotas: List<FornecedorNdd>): ByteArray {
    val wb = workbook {
      val headerStyle = cellStyle("Header") {
        fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        fillPattern = FillPatternType.SOLID_FOREGROUND
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val rowStyle = cellStyle("Row") {
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val stNotas = sheet("Notas SAP") {
        val headers = campos.map { it.header }
        row(headers, headerStyle)
        listaNotas.sortedBy { it.vendno }.forEach { nota ->
          val valores = campos.map { it.produceValue(nota) }
          row(valores, rowStyle)
        }
      }

      campos.forEachIndexed { index, _ ->
        stNotas.autoSizeColumn(index)
      }
    }
    val outBytes = ByteArrayOutputStream()
    wb.write(outBytes)
    return outBytes.toByteArray()
  }
}