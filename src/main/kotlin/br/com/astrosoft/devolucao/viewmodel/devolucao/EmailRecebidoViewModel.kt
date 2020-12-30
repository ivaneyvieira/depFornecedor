package br.com.astrosoft.devolucao.viewmodel.devolucao

import br.com.astrosoft.devolucao.model.beans.EmailDB
import br.com.astrosoft.devolucao.model.beans.EmailGmail
import br.com.astrosoft.devolucao.model.beans.Fornecedor
import br.com.astrosoft.devolucao.model.beans.NotaSaida
import br.com.astrosoft.framework.model.MailGMail
import br.com.astrosoft.framework.viewmodel.fail

class EmailRecebidoViewModel(val viewModel: DevFornecedorViewModel): IEmailViewModel {
  private val subView
    get() = viewModel.view.tabEmailRecebido
  
  fun updateGridNota() {
    subView.updateGrid(listEmailRecebido())
  }
  
  private fun listEmailRecebido() = EmailDB.listEmailRecebidos()
    .sortedWith(compareByDescending<EmailDB> {it.data}.thenByDescending {it.hora})
  
  override fun listEmail(fornecedor: Fornecedor?): List<String> = emptyList()
  
  override fun enviaEmail(gmail: EmailGmail, notas: List<NotaSaida>) = viewModel.exec {
    val mail = MailGMail()
    val enviadoComSucesso = mail.sendMail(gmail.email,
                                          gmail.assunto,
                                          gmail.msgHtml,
                                          emptyList())
    if(!enviadoComSucesso) fail("Erro ao enviar e-mail")
  }
}

interface IEmailRecebido {
  fun updateGrid(listEmailRecebido: List<EmailDB>)
}