import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.core.model.order.OrderModel;
import org.apache.commons.collections.CollectionUtils;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'A07300700','A07337417','A07338925','A07339622','A07339707','A07339976','A07347190','A07349630','A07349853','A07349879','A07353550','A07355536','A07355728','A07355731','A07359295','A07359506','A07359698','A07361303','A07361429','A07361729','A07361828','A07361902','A07366014','A07366051','A07366138','A07366160','A07366306','A07366460','A07366482','A07366517','A07366523','A07366667','A07366703','A07366762','A07366975','A07369033','A07369049','A07369052','A07369132','A07369410','A07369515','A07369555','A07369666','A07371185','A07373036','E07361441','E07366490','V07361556','V07366403' )"
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
	orderCode = currentAccountOrinedByOrderText.size() != null && currentAccountOrinedByOrderText.size()  < 10 ? currentAccountaOrinedByOrderText : currentAccountOrinedByOrderText.substring(7, currentAccountOrinedByOrderText.size() );
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