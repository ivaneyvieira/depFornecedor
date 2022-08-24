package br.com.astrosoft.devolucao.viewmodel.saida

import br.com.astrosoft.devolucao.model.beans.FiltroReimpressao
import br.com.astrosoft.devolucao.model.beans.Loja
import br.com.astrosoft.devolucao.model.beans.ReimpressaoNota
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.framework.viewmodel.fail

class TabSaidaReimpressaoViewModel(val viewModel: SaidaViewModel) {
  val subView
    get() = viewModel.view.tabSaidaReimpressaoViewModel

  fun updateView() {
    val filtro = subView.filtro()
    val resultList = ReimpressaoNota.findReimpressao(filtro)
    subView.updateGrid(resultList)
  }

  fun findLojas(): List<Loja> {
    return Loja.allLojas().sortedBy { it.no }
  }

  fun removeReimpressao(selectedItems: Set<ReimpressaoNota>) = viewModel.exec {
    if (selectedItems.isEmpty()) fail("Nenhum item selecionado")
    else {
      subView.confirmaRemocao {
        selectedItems.forEach { reimpressaoNota ->
          reimpressaoNota.remove()
        }
        updateView()
      }
    }
  }
}

interface ITabSaidaReimpressaoViewModel : ITabView {
  fun filtro(): FiltroReimpressao
  fun updateGrid(itens: List<ReimpressaoNota>)
  fun confirmaRemocao(exec: () -> Unit)
}