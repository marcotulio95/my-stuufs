import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

def limitDate = getNewDateFromString("2019-11-31");

def orderService = spring.getBean("orderService");
def flexibleSearchService = spring.getBean("flexibleSearchService");
def queryString = "SELECT {c:pk} FROM { Customer as c JOIN CurrentAccount as ca ON {c.pk}={ca.customer} JOIN CurrentAccountType as catype ON {catype.pk}={ca.type}} "
FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
flexibleSearchQuery.setCount(100);
def result = flexibleSearchService.search(flexibleSearchQuery);
println result.getResult().size();
/*
println "cliente;credito até 31-11-2019;debitos;saldoDisponivel"

for ( customer in result.getResult() ){
    def currentAccountListByTypeLimitDate = getCurrentAccountListByTypeLimitDate(customer, limitDate);
    
    def currentAccountListByTypeLimitDateAndCreditType = getCurrentAccountListByType(currentAccountListByTypeLimitDate, "CREDIT");
    def currentAccountListByDebit = getCurrentAccountListByType(customer.getCurrentAccountList(), "DEBIT");
    def currentAccountListByReservedAmount = getCurrentAccountListByType(customer.getCurrentAccountList(), "RESERVED_AMOUNT");
    
    def currentAccountListByDebitAndReservedAmount = []; 
    currentAccountListByDebitAndReservedAmount.addAll(currentAccountListByDebit);
    currentAccountListByDebitAndReservedAmount.addAll(currentAccountListByReservedAmount);

    def creditAmountByLimitDate =  getAmountFromCurrentAccountList(currentAccountListByTypeLimitDateAndCreditType);
    def debitAmount = getAmountFromCurrentAccountList(currentAccountListByDebitAndReservedAmount);
    def amountAvailable = creditAmountByLimitDate - debitAmount;

    println "$customer.uid;$creditAmountByLimitDate;$debitAmount;$amountAvailable";

}


def getNewDateFromString(stringDate){
 return Date.parse("yyyy-MM-dd hh:mm:ss", "$stringDate 23:59:59")
}

def getCurrentAccountListByTypeLimitDate(customer, limitDate){
    def currentAccounByLimitDateList = [];
    for ( currentAccount in customer.getCurrentAccountList() ){
      if(currentAccount.transactionDate.compareTo(limitDate) <= 0){
        currentAccounByLimitDateList.add(currentAccount)
      }
    }
  return currentAccounByLimitDateList;
}

def getCurrentAccountListByType( currentAccountList, currentAccountTypeTarget){
    def currentAccounByTypeList = [];
    for ( currentAccount in currentAccountList ){
      if(currentAccount.type.code == currentAccountTypeTarget ){
        currentAccounByTypeList.add(currentAccount)
      }
    }
    return currentAccounByTypeList;
}

def getAmountFromCurrentAccountList(currentAccountList){
    def amount = 0;
    for ( currentAccount in currentAccountList ){
        amount = amount + currentAccount.amount;
    }
    return amount;
}
*/