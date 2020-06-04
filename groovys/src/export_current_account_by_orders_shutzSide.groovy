import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.core.model.order.OrderModel;
import org.apache.commons.collections.CollectionUtils;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'A06942925','A06946478','A06952459','A06952888','A06954875','A06954884','A06956227','A06956928','A06961838','A06961869','A06961879','A06961979','A06961985','A06963284','A06963399','A06963589','A06963633','A06963645','A06963646','A06963649','A06963663','A06963686','A06963778','A06963933','A06963974','A06964005','A06964353','A06964577','A06964684','A06964754','A06964794','A06964804','A06964862','A06964869','A06964969','A06964987','A06967276','A06967544','A06967551','A06967568','A06967585','A06967595','A06967681','A06967683','A06967735','A06967750','A06967818','A06967884','A06967916','A06967992','A06967997','A06967998','A06967999','A06969035','A06969055','A06969074','A06969103','A06969147','A06969262','A06969362','A06969426','A06969492','A06969535','A06969586','A06969664','A06969766','A06969880','A06969926','A06969992','A06969999','A06971049','A06971107','A06971116','A06971118','A06971189','A06971421','A06971427','A06971439','A06971939','A06971984','A06972033','A06972129','A06972206','A06972211','A06972327','A06972446','A06972517','A06972815','A06972854','A06972896','A06972915','A06972951','A06972979','A06973054','A06973160','A06973164','A06973213','A06973405','A06973421','A06973427','A06973467','A06973481','A06973501','A06973536','A06973684','A06973725','A06973781','A06973824','A06973925','A06976120','A06976276','A06976678','A06976742','A06980036','A06980064','A06980120','A06980210','A06980363','A06980496','A06980534','A06980552','A06980666','A06980767','A06981037','A06981139','A06981185','A06981246','A06981317','A06981412','A06981417','A06981424','A06981513','A06981647','A06981747','A06986140','A06986178','A06992105','A06992106','A06992124','A06992129','A06993034','A06993035','A06993051','A06993077','A06993183','A06994065','A06994076','A06994105','A06995046','A06995084','A06995091','A06995111','B06955973','B06972917','B06976737','B06980104','B06981511','E06969092','E06971171','E06971249','E06971335','E06973343','E06976018','V06942876','V06955529','V06967663','V06972352','V06976354','V06976420','V06980118','V06980152','V06980374','V06980479' )"
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