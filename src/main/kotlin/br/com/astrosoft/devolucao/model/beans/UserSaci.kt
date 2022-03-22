package br.com.astrosoft.devolucao.model.beans

import br.com.astrosoft.devolucao.model.saci
import br.com.astrosoft.framework.model.IUser
import kotlin.math.pow
import kotlin.reflect.KProperty

class UserSaci : IUser {
  var no: Int = 0
  var name: String = ""
  override var login: String = ""
  override var senha: String = ""
  var bitAcesso: Long = 0
  var storeno: Int = 0
  var prntno: Int = 0
  var impressora: String? = ""
  override var ativo by DelegateAuthorized(0)
  var nota01 by DelegateAuthorized(1)
  var nota66 by DelegateAuthorized(2)
  var pedido by DelegateAuthorized(3)
  var nota66Pago by DelegateAuthorized(4)
  var emailRecebido by DelegateAuthorized(5)
  var notaPendente by DelegateAuthorized(6)
  var agendaAgendada by DelegateAuthorized(7)
  var agendaNaoAgendada by DelegateAuthorized(8)
  var agendaRecebida by DelegateAuthorized(9)
  var entrada by DelegateAuthorized(10)
 // var nota01Coleta by DelegateAuthorized(11)
  var remessaConserto by DelegateAuthorized(12)
  var ajusteGarantia by DelegateAuthorized(13)
  var notaFinanceiro by DelegateAuthorized(14)
  var conferenciaSap by DelegateAuthorized(15)
  var sap by DelegateAuthorized(16)
  var entradaNdd by DelegateAuthorized(17)
  var entradaNddReceber by DelegateAuthorized(18)
  var entradaNddRecebido by DelegateAuthorized(19)
  var entradaNddNFPrec by DelegateAuthorized(20)
  var notaSaida by DelegateAuthorized(21)
  var ajusteGarantiaPago by DelegateAuthorized(22)
  var entradaNddNFPrecInfo by DelegateAuthorized(23)
  var desconto by DelegateAuthorized(24)
  var forPendenteBASE by DelegateAuthorized(25)
  var forPendenteNOTA by DelegateAuthorized(26)
  var forPendenteEMAIL by DelegateAuthorized(27)
  var forPendenteTRANSITO by DelegateAuthorized(28)
  var forPendenteFABRICA by DelegateAuthorized(29)
  var forPendenteCREDITO_AGUARDAR by DelegateAuthorized(30)
  var forPendenteCREDITO_CONCEDIDO by DelegateAuthorized(31)
  var forPendenteCREDITO_APLICADO by DelegateAuthorized(32)
  var forPendenteCREDITO_CONTA by DelegateAuthorized(33)
  var forPendenteBONIFICADA by DelegateAuthorized(34)
  var forPendenteREPOSICAO by DelegateAuthorized(35)
  var forPendenteRETORNO by DelegateAuthorized(36)
  var pedidoNFD by DelegateAuthorized(37)

  val forPendente
    get() = forPendenteBASE || forPendenteNOTA || forPendenteEMAIL || forPendenteTRANSITO || forPendenteFABRICA || forPendenteCREDITO_AGUARDAR || forPendenteCREDITO_CONCEDIDO || forPendenteCREDITO_APLICADO || forPendenteCREDITO_CONTA || forPendenteBONIFICADA || forPendenteREPOSICAO || forPendenteRETORNO

  val menuDevolucao01
    get() = nota01 || /*nota01Coleta ||*/ remessaConserto || ajusteGarantia || notaFinanceiro || conferenciaSap || sap
  val menuDevolucaoPedido
    get() = pedido || pedidoNFD
  val menuDevolucao66
    get() = nota66 || nota66Pago || entrada || emailRecebido
  val menuRecebimento
    get() = notaPendente
  val menuAgenda
    get() = agendaAgendada || agendaNaoAgendada || agendaRecebida
  val menuEntrada
    get() = entradaNdd || entradaNddReceber || entradaNddRecebido || entradaNddNFPrec || entradaNddNFPrecInfo
  val menuSaida
    get() = notaSaida
  override val admin
    get() = login == "ADM"

  companion object {
    fun findAll(): List<UserSaci> {
      return saci.findAllUser().filter { it.ativo }
    }

    fun updateUser(user: UserSaci) {
      saci.updateUser(user)
    }

    fun findUser(login: String?): UserSaci? {
      return saci.findUser(login)
    }
  }
}

class DelegateAuthorized(numBit: Int) {
  private val bit = 2.toDouble().pow(numBit).toLong()

  operator fun getValue(thisRef: UserSaci?, property: KProperty<*>): Boolean {
    thisRef ?: return false
    return (thisRef.bitAcesso and bit) != 0L || thisRef.admin
  }

  operator fun setValue(thisRef: UserSaci?, property: KProperty<*>, value: Boolean?) {
    thisRef ?: return
    val v = value ?: false
    thisRef.bitAcesso = when {
      v    -> thisRef.bitAcesso or bit
      else -> thisRef.bitAcesso and bit.inv()
    }
  }
}


