import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.paginated.PaginatedFlexibleSearchParameter;
import java.util.Map;
import java.util.HashMap;
import br.com.arezzoco.core.model.FavoriteListElementModel;

def webPaginationUtils = spring.getBean("webPaginationUtils"); 
def paginatedFlexibleSearchService =  spring.getBean("paginatedFlexibleSearchService");
def query = "SELECT {p:pk} from { FavoriteListElement  AS p} WHERE {p:customer}='8801434304516'"

def flexibleSearchQuery = new FlexibleSearchQuery(query);
def flexibleSearchParameter = new PaginatedFlexibleSearchParameter();

Map<String, String> params = new HashMap<>();
params.put("currentPage", "1");
params.put("pageSize", "6");

def searchPageDataRequest = webPaginationUtils.buildSearchPageData(params);


flexibleSearchParameter.setFlexibleSearchQuery(flexibleSearchQuery);
flexibleSearchParameter.setSearchPageData(searchPageDataRequest);
def searchPageDataResult =  paginatedFlexibleSearchService.<FavoriteListElementModel>search(flexibleSearchParameter).getResults();