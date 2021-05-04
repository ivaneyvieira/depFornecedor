package br.com.astrosoft.devolucao.view.reports

import br.com.astrosoft.devolucao.model.beans.Fornecedor
import br.com.astrosoft.devolucao.model.beans.NotaSaida
import br.com.astrosoft.devolucao.view.reports.Templates.fieldFontGrande
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.DynamicReports.*
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.*
import net.sf.dynamicreports.report.constant.PageOrientation.PORTRAIT
import net.sf.dynamicreports.report.constant.PageType.A4
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import java.io.ByteArrayOutputStream

class RelatorioFornecedor(val fornecedor: Fornecedor) {
  val lojaCol = col.column("loja", NotaSaida::loja.name, type.integerType()).apply {
    this.setHorizontalTextAlignment(RIGHT)
    this.setFixedWidth(40)
  }

  val dataNotaCol = col.column("Data", NotaSaida::dataNotaString.name, type.stringType()).apply {
    this.setHorizontalTextAlignment(RIGHT)
    this.setFixedWidth(50)
  }

  val notaInvCol = col.column("Nota", NotaSaida::nota.name, type.stringType()).apply {
    this.setHorizontalTextAlignment(RIGHT)
    this.setFixedWidth(50)
  }

  val faturaCol = col.column("Fatura", NotaSaida::fatura.name, type.stringType()).apply {
    this.setHorizontalTextAlignment(RIGHT)
    this.setFixedWidth(50)
  }

  val valorCol = col.column("Valor", NotaSaida::valor.name, type.doubleType()).apply {
    this.setPattern("#,##0.00")
    this.setHorizontalTextAlignment(RIGHT)
    this.setFixedWidth(80)
  }


  private fun columnBuilder(): List<TextColumnBuilder<out Any>> {
    return listOf(lojaCol, dataNotaCol, notaInvCol, faturaCol, valorCol)
  }

  private fun titleBuider(): ComponentBuilder<*, *> {
    val largura = 40 + 50 + 50 + 50 + 80
    return verticalBlock {
      horizontalList {
        text("FONECEDOR", CENTER, largura).apply {
          this.setStyle(fieldFontGrande)
        }
      }
      horizontalList {
        text("${fornecedor.custno} ${fornecedor.fornecedor} (${fornecedor.vendno})", LEFT, largura)
      }
      horizontalList {
        text("")
      }
    }
  }

  private fun pageFooterBuilder(): ComponentBuilder<*, *>? {
    return cmp.verticalList()
  }

  private fun subtotalBuilder(): List<SubtotalBuilder<*, *>> {
    return listOf(
      sbt.text("Total R$", faturaCol),
      sbt.sum(valorCol),
                 )
  }

  fun makeReport(): JasperReportBuilder {
    val colunms = columnBuilder().toTypedArray()
    val itens = fornecedor.notas
    val pageOrientation = PORTRAIT
    return report().title(titleBuider())
            .setTemplate(Templates.reportTemplate)
            .columns(* colunms)
            .columnGrid(* colunms)
            .setDataSource(itens)
            .setPageFormat(A4, pageOrientation)
            .setPageMargin(margin(28))
            .summary(pageFooterBuilder())
            .subtotalsAtSummary(* subtotalBuilder().toTypedArray())
            .setSubtotalStyle(stl.style().setPadding(2).setTopBorder(stl.pen1Point()))
            .pageFooter(cmp.pageNumber().setHorizontalTextAlignment(RIGHT).setStyle(stl.style().setFontSize(8)))
  }

  companion object {
    fun processaRelatorio(fornecedor: Fornecedor): ByteArray {
      val report = RelatorioFornecedor(fornecedor).makeReport()
      val printList = listOf(report.toJasperPrint())
      val exporter = JRPdfExporter()
      val out = ByteArrayOutputStream()
      exporter.setExporterInput(SimpleExporterInput.getInstance(printList))

      exporter.exporterOutput = SimpleOutputStreamExporterOutput(out)

      exporter.exportReport()
      return out.toByteArray()
    }
  }
}

