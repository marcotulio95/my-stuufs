def flexibleSearchService = spring.getBean("flexibleSearchService");

def query = "SELECT {order} FROM {AbstractOrderEntry} where {product} is null"
def result = flexibleSearchService.search(query);
def orders =  result.getResult();

modelService.removeAll(orders);