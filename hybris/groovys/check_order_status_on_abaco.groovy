//def orderCancelService = spring.getBean("orderCancelService");
//orderCancelService.cancelDenialStrategies

import br.com.arezzoco.integration.abacos.wsclient.plataforma.ArrayOfString;
def abacosPlatformServiceWrapper = spring.getBean("abacosPlatformServiceWrapper");
final ArrayOfString pedidosArray = new ArrayOfString();

pedidosArray.getString().addAll(Arrays.asList('00018010'));

abacosPlatformServiceWrapper.pedidoExiste( pedidosArray , 'arezzo' );



http://wsarezzohom.apiecomm.com.br:8090/IntegradorAbacos.asmx -> actual