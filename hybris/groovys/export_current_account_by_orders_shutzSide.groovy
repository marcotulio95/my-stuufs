import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.core.model.order.OrderModel;
import org.apache.commons.collections.CollectionUtils;

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'C33560009','C33569436','C33583139','C33584462','C33586553','C33586689','C33586772','C33593114','C33596011','C33596043','C33596083','C33596244','C33597147','C33597289','C33598181','C33598359','C33598361','C33598542','F33569552','F33586258','N33569593','N33583994','N33584904','N33586726','N33586812','N33586950','N33593716','N33597196','N33598342','N33598531','S33544923','S33571360','S33571482','S33583472','S33583788','S33583916','S33584784','S33585656','S33585851','S33585912','S33586674','S33586699','S33586701','S33586973','S33593606','S33596108','S33596111','S33597020','S33598229')"
def result = flexibleSearchService.search(query);
def alreadyChecked = [];

for ( order in result.getResult() ){
   if(!alreadyChecked.contains(order.getCode())){
    alreadyChecked.add(order.getCode());
    def customer = order.getUser();
    for( currentAccount in customer.getCurrentAccountList()){
      if (currentAccount.getType().equals(CurrentAccountType.CREDIT)){
            
            def currentAccountOrinedByOrderText = currentAccount.getOtherReasons();
            def currentAccountOrinedByOrderCode = currentAccountOrinedByOrderText.size() < 10 ? currentAccountOrinedByOrderText : currentAccountOrinedByOrderText.substring(10, currentAccountOrinedByOrderText.size());
            def currentAccountOrinedByOrderModel = getOrderByCode(currentAccountOrinedByOrderCode);
            def paymentMode = currentAccountOrinedByOrderModel != null ? currentAccountOrinedByOrderModel.getPaymentInfo() : currentAccountOrinedByOrderText;
			
            println order.getCode()+";"+customer.getUid()+";"+currentAccount.getAmount()+";"+currentAccount.getOtherReasons()+";"+paymentMode
        }
    }    
   }
}


def getOrderByCode(orderCode){
    def queryToGetOrder = "SELECT {o.pk}  FROM { Order AS o }  WHERE {originalversion} is null and { o.code }='"+orderCode+"'";
    def result = flexibleSearchService.search(queryToGetOrder).getResult();
  	return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
}


