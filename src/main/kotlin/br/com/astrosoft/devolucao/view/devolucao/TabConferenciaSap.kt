package br.com.astrosoft.devolucao.view.devolucao

import br.com.astrosoft.devolucao.model.beans.*
import br.com.astrosoft.devolucao.view.devolucao.columns.FornecedorSapViewColumns.fornecedorCodigoSaci
import br.com.astrosoft.devolucao.view.devolucao.columns.FornecedorSapViewColumns.fornecedorCodigoSap
import br.com.astrosoft.devolucao.view.devolucao.columns.FornecedorSapViewColumns.fornecedorNome
import br.com.astrosoft.devolucao.view.devolucao.columns.FornecedorSapViewColumns.fornecedorUltimaData
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSaidaViewColumns.notaDataNota
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSaidaViewColumns.notaFatura
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSaidaViewColumns.notaLoja
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSaidaViewColumns.notaNota
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSaidaViewColumns.notaValor
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSapViewColumns.notaSapData
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSapViewColumns.notaSapLoja
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSapViewColumns.notaSapNotaSaci
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSapViewColumns.notaSapNumero
import br.com.astrosoft.devolucao.view.devolucao.columns.NotaSapViewColumns.notaSapTotal
import br.com.astrosoft.devolucao.viewmodel.devolucao.ITabConferenciaSap
import br.com.astrosoft.devolucao.viewmodel.devolucao.TabConferenciaSapViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.SubWindowForm
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.list
import com.github.mvysny.karibudsl.v10.getColumnBy
import com.github.mvysny.karibudsl.v10.h3
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.SINGLE
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.upload.FileRejectedEvent
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer
import com.vaadin.flow.data.provider.SortDirection
import com.vaadin.flow.data.value.ValueChangeMode
import org.apache.poi.ss.formula.functions.T
import java.io.File

class TabConferenciaSap(val viewModel: TabConferenciaSapViewModel) : TabPanelGrid<FornecedorSap>(FornecedorSap::class),
        ITabConferenciaSap {
  private lateinit var edtFiltro: TextField

  override fun HorizontalLayout.toolBarConfig() {
    edtFiltro = textField("Filtro") {
      width = "400px"
      valueChangeMode = ValueChangeMode.TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }
    val (buffer, upload) = uploadFileXls()
    upload.addSucceededListener {
      val fileName = "/tmp/${it.fileName}"
      val bytes = buffer.getInputStream(it.fileName).readBytes()
      val file = File(fileName)
      file.writeBytes(bytes)
      viewModel.readExcel(fileName)
    }
  }

  private fun HasComponents.uploadFileXls(): Pair<MultiFileMemoryBuffer, Upload> {
    val buffer = MultiFileMemoryBuffer()
    val upload =
            Upload(buffer) //upload.setAcceptedFileTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    val uploadButton = Button("Planilha SAP")
    upload.uploadButton = uploadButton
    upload.isAutoUpload = true
    upload.maxFileSize = 1024 * 1024 * 1024
    upload.addFileRejectedListener { event: FileRejectedEvent ->
      println(event.errorMessage)
    }
    upload.addFailedListener { event ->
      println(event.reason.message)
    }
    add(upload)
    return Pair(buffer, upload)
  }

  override fun Grid<FornecedorSap>.gridPanel() {
    addColumnButton(VaadinIcon.FILE_TABLE, "Notas", "Notas") { fornecedor ->
      DlgNotaSapSaci(viewModel).showDialogNota(fornecedor)
    }

    fornecedorCodigoSaci()
    fornecedorCodigoSap()
    fornecedorNome()
    fornecedorUltimaData()

    sort(listOf(GridSortOrder(getColumnBy(FornecedorSap::nome), SortDirection.ASCENDING)))
  }

  override fun filtro(): String {
    return if (this::edtFiltro.isInitialized) edtFiltro.value ?: ""
    else ""
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.conferenciaSap == true
  }

  override val label: String
    get() = "Sap x Saci"

  override fun updateComponent() {
    viewModel.updateView()
  }
}

class DlgNotaSapSaci(val viewModel: TabConferenciaSapViewModel) {
  fun showDialogNota(fornecedor: FornecedorSap?) {
    fornecedor ?: return

    val listNotasSap = fornecedor.notas
    val listNotasSaci = fornecedor.notasSaci()
    val form = SubWindowForm(fornecedor.labelTitle, toolBar = {}) {
      val gridNota = createGridSap(listNotasSap, "Notas SAP")
      val gridPedido = createGridSaci(listNotasSaci, "Notas Saci")
      gridNota.onSelect { nota ->
        val notaSaida = listNotasSaci.firstOrNull { it.nota == nota?.nfSaci }
        gridPedido.selectRow(notaSaida)
      }
      gridPedido.onSelect { nota ->
        val notaSaida = listNotasSap.firstOrNull { it.nfSaci == nota?.nota }
        gridNota.selectRow(notaSaida)
      }

      HorizontalLayout().apply {
        setSizeFull()
        addAndExpand(gridNota, gridPedido)
      }
    }
    form.open()
  }

  private fun createGridSap(listParcelas: List<NotaDevolucaoSap>, label: String): GridLabel<NotaDevolucaoSap> {
    val gridDetail = Grid(NotaDevolucaoSap::class.java, false)
    val grid = gridDetail.apply {
      setSizeFull()
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setSelectionMode(SINGLE)
      setItems(listParcelas)

      notaSapLoja()
      notaSapNumero()
      notaSapNotaSaci()
      notaSapData()
      notaSapTotal().apply {
        val totalPedido = listParcelas.sumOf { it.saldo }.format()
        setFooter(Html("<b><font size=4>Total R$ &nbsp;&nbsp;&nbsp;&nbsp; ${totalPedido}</font></b>"))
      }

      //sort(listOf(GridSortOrder(getColumnBy(NotaDevolucaoSap::nfSaci), SortDirection.ASCENDING)))

      listParcelas.forEach { parcela ->
        this.setDetailsVisible(parcela, true)
      }
    }
    return GridLabel(grid, label)
  }

  private fun createGridSaci(listPedidos: List<NotaSaida>, label: String): GridLabel<NotaSaida> {
    val gridDetail = Grid(NotaSaida::class.java, false)
    val grid = gridDetail.apply {
      setSizeFull()
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setSelectionMode(SINGLE)
      setItems(listPedidos)

      notaLoja()
      notaNota()
      notaFatura()
      notaDataNota()
      notaValor().apply {
        val totalPedido = listPedidos.sumOf { it.valorNota }.format()
        setFooter(Html("<b><font size=4>Total R$ &nbsp;&nbsp;&nbsp;&nbsp; ${totalPedido}</font></b>"))
      }

      //sort(listOf(GridSortOrder(getColumnBy(NotaSaida::nota), SortDirection.ASCENDING)))

      listPedidos.forEach { parcela ->
        this.setDetailsVisible(parcela, true)
      }

    }
    return GridLabel(grid, label)
  }
}

class GridLabel<T : Any>(val grid: Grid<T>, label: String) : VerticalLayout() {
  fun selectRow(notaSaida: T?) {
    if (notaSaida != null) {
      grid.select(notaSaida)
      val list = list(grid)
      val index = list.indexOf(notaSaida)
      if (index >= 0) grid.scrollToIndex(index)
    }
    else grid.deselectAll()
  }

  fun onSelect(selectBlock: (T?) -> Unit) {
    grid.addSelectionListener {
      if (it.isFromClient) selectBlock(it.allSelectedItems.firstOrNull())
    }
  }

  init {
    this.h3(label)
    this.addAndExpand(grid)
  }
}