import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.core.model.order.OrderModel;
import org.apache.commons.collections.CollectionUtils;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'A07338523','A07347511','A07347647','A07353604','A07355231','A07355996','A07359719','A07359815','A07369811','A07369904','A07369950','A07369968','A07369987','A07370008','A07371485','A07371517','A07371619','A07371624','A07371670','A07371683','A07371711','A07371721','A07371738','A07371821','A07371961','A07371985','A07373448','A07373453','A07373500','A07373526','A07373534','A07373562','A07373605','A07373665','A07373725','A07373752','A07373782','A07373837','A07373916','A07373964','A07374007','A07375078','A07375190','A07375213','A07375274','A07375298','A07375299','A07375403','A07375421','A07375607','A07375759','A07375794','A07375848','A07375868','A07380027','A07380038','A07380055','A07380057','A07380079','A07380148','A07380459','A07380527','A07380692','A07380867','A07381007','A07382203','A07382265','A07382324','A07382339','A07382376','A07382489','A07382518','A07382654','A07382864','A07382877','A07384070','A07384150','A07384269','A07384382','A07384553','A07384692','A07384907','A07385026','A07385038','A07385242','A07385451','A07385504','A07385787','A07388257','A07388262','A07388315','A07388386','A07388578','A07389127','A07389315','A07389723','A07390088','A07390142','A07391026','B07373829','E07369542','E07389247','E07391022','V07361033','V07361827','V07369468','V07373939','V07375030','V07384749','V07388036','V07389185','V07389652','V07389800' )"
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