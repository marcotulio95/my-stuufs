import br.com.arezzoco.enums.CurrentAccountType;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

def limitDate = getNewDateFromString("2019-11-31");
def PageableData pageableData = new PageableData();
def pageActual = 1;
def pageSize = 10000;
pageableData.setCurrentPage(pageActual);
pageableData.setPageSize(pageSize);

def orderService = spring.getBean("orderService");
def pagedFlexibleSearchService = spring.getBean("pagedFlexibleSearchService");
def queryString = "SELECT DISTINCT {c:pk} FROM { Customer as c JOIN CurrentAccount as ca ON {c.pk}={ca.customer} JOIN CurrentAccountType as catype ON {catype.pk}={ca.type}} order by {c.pk} "
FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
def result = pagedFlexibleSearchService.search(flexibleSearchQuery,pageableData);

println "mostrando pagina $pageActual de $result.pagination.numberOfPages"
println "mostrando $pageSize resultados de $result.pagination.totalNumberOfResults" 
println "cliente;crédito-até-31-11-2019;debitos;saldo-disponivel"

def customers = result.getResults();
for ( customer in customers ){
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
    
    if(amountAvailable > 0){
      println "$customer.uid;$creditAmountByLimitDate;$debitAmount;$amountAvailable";
    }else{
      print "XX"
    }
    
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
