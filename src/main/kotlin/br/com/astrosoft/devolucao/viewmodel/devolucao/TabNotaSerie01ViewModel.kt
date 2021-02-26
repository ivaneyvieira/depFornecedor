package br.com.astrosoft.devolucao.viewmodel.devolucao

class TabNotaSerie01ViewModel(viewModel: DevolucaoViewModel) :
        TabDevolucaoViewModelAbstract(viewModel) {
    override val subView
        get() = viewModel.view.tabNotaSerie01
}

interface ITabNotaSerie01 : ITabNota {
    override val serie: String
        get() = "1"
    override val pago66: String
        get() = ""
    override val coleta01: String
        get() = ""
}