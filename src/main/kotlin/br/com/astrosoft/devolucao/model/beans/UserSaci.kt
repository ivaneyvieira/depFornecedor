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
  var senhaPrint: String? = ""
  override var ativo by DelegateAuthorized(0)
  var nota01 by DelegateAuthorized(1)
  var nota66 by DelegateAuthorized(2)
  var pedidoEditor by DelegateAuthorized(3)
  var nota66Pago by DelegateAuthorized(4)
  var emailRecebido by DelegateAuthorized(5)
  var notaPendente by DelegateAuthorized(6)
  var agendaAgendada by DelegateAuthorized(7)
  var agendaPreEntrada by DelegateAuthorized(8)
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
  var entradaNddTribFiscal by DelegateAuthorized(20)
  var notaSaida by DelegateAuthorized(21)
  var ajusteGarantiaPago by DelegateAuthorized(22)
  var entradaNddRefFiscal by DelegateAuthorized(23)
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
  //var pedidoNFD by DelegateAuthorized(37)

  //var pedidoPago by DelegateAuthorized(38)
  var forPendenteAGUARDA_COLETA by DelegateAuthorized(39)
  var forPendenteASSINA_CTE by DelegateAuthorized(40)
  var ajusteGarantiaPerca by DelegateAuthorized(41)

  //var pedidoAjuste by DelegateAuthorized(42)
  var ajusteGarantiaPendente by DelegateAuthorized(43)
  var ajusteGarantia66 by DelegateAuthorized(44)

  //var pedidoBaixa by DelegateAuthorized(45)
  //var pedidoEmail by DelegateAuthorized(46)
  var pedidoPendente by DelegateAuthorized(47)

  //var pedidoLiberado by DelegateAuthorized(48)
  //var pedidoPerca by DelegateAuthorized(49)
  //var pedidoDescarte by DelegateAuthorized(50)
  var pedidoFornecedor by DelegateAuthorized(51)

  //var pedidoRetorno by DelegateAuthorized(52)
  var entradaNddNFEntrada by DelegateAuthorized(53)
  var notaSaidaCopia by DelegateAuthorized(54)
  var notaSaida2Via by DelegateAuthorized(55)
  var notaSaidaReimpressao by DelegateAuthorized(56)
  var reimpressao by DelegateAuthorized(57)
  var compraPedidos by DelegateAuthorized(58)
  val compraConferir by DelegateAuthorized(59)
  var entradaFrete by DelegateAuthorized(60)
  var entradaPreco by DelegateAuthorized(61)
  var entradaPrecoPreRec by DelegateAuthorized(62)
  var entradaFretePer by DelegateAuthorized(63)
  var compraConferido by DelegateAuthorized(64)
  var agendaRastreamento by DelegateAuthorized(65)
  var demandaAgenda by DelegateAuthorized(66)
  var demandaConcluido by DelegateAuthorized(67)
  var entradaCte by DelegateAuthorized(68)
  var entradaFileNFE by DelegateAuthorized(69)
  var pedidoFinalizado by DelegateAuthorized(70)
  var entradaSped by DelegateAuthorized(71)
  var preEntradaPreEnt by DelegateAuthorized(72)
  var preEntradaFiscal by DelegateAuthorized(73)
  var entradaSped2 by DelegateAuthorized(74)
  var entradaSTEstado by DelegateAuthorized(75)

  val forPendente
    get() = forPendenteBASE || forPendenteNOTA || forPendenteEMAIL || forPendenteTRANSITO || forPendenteFABRICA || forPendenteCREDITO_AGUARDAR || forPendenteCREDITO_CONCEDIDO || forPendenteCREDITO_APLICADO || forPendenteCREDITO_CONTA || forPendenteBONIFICADA || forPendenteREPOSICAO || forPendenteRETORNO || forPendenteAGUARDA_COLETA || forPendenteASSINA_CTE
  val menuDemanda
    get() = demandaAgenda || demandaConcluido

  val menuDevolucao01
    get() = nota01 || /*nota01Coleta ||*/ remessaConserto || notaFinanceiro || conferenciaSap || sap
  val menuDevolucaoInterna
    get() = ajusteGarantia || ajusteGarantiaPago || ajusteGarantiaPerca || ajusteGarantiaPendente || ajusteGarantia66
  val menuDevolucaoPedido
    get() = pedidoEditor || pedidoPendente || pedidoFinalizado
  val menuDevolucao66
    get() = nota66 || nota66Pago || entrada || emailRecebido
  val menuRecebimento
    get() = notaPendente
  val menuAgenda
    get() = agendaAgendada || agendaPreEntrada || agendaRecebida || agendaRastreamento
  val menuEntrada
    get() = entradaNdd || entradaNddReceber || entradaNddRecebido || entradaNddTribFiscal || entradaNddRefFiscal ||
        entradaFrete || entradaPreco || entradaPrecoPreRec || entradaCte || entradaFileNFE || entradaSped ||
        entradaSped2 || entradaSTEstado
  val menuCompra
    get() = compraPedidos || compraConferir
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
      v -> thisRef.bitAcesso or bit
      else -> thisRef.bitAcesso and bit.inv()
    }
  }
}


