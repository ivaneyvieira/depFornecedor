package br.com.astrosoft.devolucao.view.compra.columns

import br.com.astrosoft.devolucao.model.beans.PedidoCompraProduto
import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.kaributools.sortProperty
import com.vaadin.flow.component.grid.Grid

object PedidoCompraProdutoColumns {
  fun Grid<PedidoCompraProduto>.colItem() = addColumnString(PedidoCompraProduto::item) {
    this.setHeader("Item")
    this.sortProperty = PedidoCompraProduto::linha
  }

  fun Grid<PedidoCompraProduto>.colCodigo() = addColumnString(PedidoCompraProduto::codigo) {
    this.setHeader("Código")
  }

  fun Grid<PedidoCompraProduto>.colDescricao() = addColumnString(PedidoCompraProduto::descricao) {
    this.setHeader("Descrição")
    this.isExpand = true
  }

  fun Grid<PedidoCompraProduto>.colDescNota() = addColumnString(PedidoCompraProduto::refname) {
    this.setHeader("Desc Nota")
    this.isExpand = true
  }


  fun Grid<PedidoCompraProduto>.colRefFabrica() = addColumnString(PedidoCompraProduto::refFab) {
    this.setHeader("Ref Fab")
  }

  fun Grid<PedidoCompraProduto>.colRefNota() = addColumnString(PedidoCompraProduto::refno) {
    this.setHeader("Ref NF")
  }

  fun Grid<PedidoCompraProduto>.colGrade() = addColumnString(PedidoCompraProduto::grade) {
    this.setHeader("Grade")
  }

  fun Grid<PedidoCompraProduto>.colRefDif() = addColumnString(PedidoCompraProduto::difRef) {
    this.setHeader("Dif Ref")
  }

  fun Grid<PedidoCompraProduto>.colUnidade() = addColumnString(PedidoCompraProduto::unidade) {
    this.setHeader("Und")
  }

  fun Grid<PedidoCompraProduto>.colCusto() = addColumnDouble(PedidoCompraProduto::custoUnit) {
    this.setHeader("V. Unit Ped")
  }

  fun Grid<PedidoCompraProduto>.colCustoCt() = addColumnDouble(PedidoCompraProduto::valorUnitarioCt) {
    this.setHeader("V. Unit Cot")
  }

  fun Grid<PedidoCompraProduto>.colCustoDif() = addColumnDouble(PedidoCompraProduto::valorUnitarioDif) {
    this.setHeader("Dif Valor")
  }

  fun Grid<PedidoCompraProduto>.colQtde() = addColumnInt(PedidoCompraProduto::qtPedida) {
    this.setHeader("Qtd Ped")
  }

  fun Grid<PedidoCompraProduto>.colQtdeCt() = addColumnInt(PedidoCompraProduto::quantidadeCt) {
    this.setHeader("Qtd Cot")
  }

  fun Grid<PedidoCompraProduto>.colQtdeDif() = addColumnInt(PedidoCompraProduto::quantidadeDif) {
    this.setHeader("Dif Qtd")
  }

  fun Grid<PedidoCompraProduto>.colVlTotal() = addColumnDouble(PedidoCompraProduto::vlPedido) {
    this.setHeader("Valor Total")
  }

  fun Grid<PedidoCompraProduto>.colBarcode() = addColumnString(PedidoCompraProduto::barcode) {
    this.setHeader("Cod.Barra")
  }
}