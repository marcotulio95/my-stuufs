import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.core.model.order.OrderModel;
import org.apache.commons.collections.CollectionUtils;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'A06860390','A06868735','A06869628','A06869825','A06871940','A06878246','A06878418','A06878709','A06878777','A06881190','A06881286','A06881880','A06881999','A06882840','A06883585','A06883596','A06883736','A06883793','A06883803','A06883813','A06890222','A06890236','A06890283','A06890489','A06890534','A06890659','A06890677','A06890720','A06893049','A06893217','A06893221','A06893525','A06894228','A06894232','A06894474','A06894656','A06894675','A06894677','A06897107','A06897132','A06897145','A06897196','A06897248','A06897249','A06897299','A3634591M','V06890687' )"
def result = flexibleSearchService.search(query);
def alreadyChecked = [];

for ( order in result.getResult() ){
   if(!alreadyChecked.contains(order.getCode())){
    alreadyChecked.add(order.getCode());
    def customer = order.getUser();
    for( currentAccount in customer.getCurrentAccountList()){
      if (currentAccount.getType().equals(CurrentAccountType.CREDIT)){
            
            def currentAccountOrinedByOrderText = currentAccount.getOtherReasons();
			      def currentAccountOrinedByOrderCode = getOrderCode(order.getCode(), currentAccountOrinedByOrderText);
            
            if (currentAccountOrinedByOrderCode != ""){
              
              def currentAccountOrinedByOrderModel = getOrderByCode(currentAccountOrinedByOrderCode);
              def paymentMode = currentAccountOrinedByOrderModel != null ? currentAccountOrinedByOrderModel.getPaymentInfo() : currentAccountOrinedByOrderText;
              if (paymentMode instanceof CreditCardPaymentInfoModel){
                println order.getCode()+";"+customer.getUid()+";"+currentAccount.getAmount()+";"+currentAccount.getOtherReasons()+";"+paymentMode+";"+paymentMode.number+";"+paymentMode.type.code+";"+paymentMode.validToYear
              }else{
                println order.getCode()+";"+customer.getUid()+";"+currentAccount.getAmount()+";"+currentAccount.getOtherReasons()+";"+paymentMode
              }
              
            }else{
              println order.getCode()+";"+customer.getUid()+";"+currentAccount.getAmount()+";"+"Pedido nao encontrado na reason do credito"+";"+"Meio de pagamento nao localizado, por que nao foi possivel localizar o pedido na reason do credito"
            }
            
        }
    }    
   }
}

def getOrderCode(originalOrder , currentAccountOrinedByOrderText){
  def orderCode = "";
  try{
  currentAccountOrinedByOrderText = currentAccountOrinedByOrderText.replace(".","").replaceAll("\\s", "");
	orderCode = currentAccountOrinedByOrderText.size() != null && currentAccountOrinedByOrderText.size()  < 10 ? currentAccountOrinedByOrderText : currentAccountOrinedByOrderText.substring(7, currentAccountOrinedByOrderText.size() );
  }catch(Exception e){
    //println "i cant fiind reason for this credit on order: $originalOrder"
  }
  return orderCode;
}


def getOrderByCode(orderCode){
    def queryToGetOrder = "SELECT {o.pk}  FROM { Order AS o }  WHERE {originalversion} is null and { o.code }='"+orderCode+"'";
    def result = flexibleSearchService.search(queryToGetOrder).getResult();
  	return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
}