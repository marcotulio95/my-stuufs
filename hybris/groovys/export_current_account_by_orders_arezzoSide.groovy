import br.com.arezzoco.enums.CurrentAccountType;
def flexibleSearchService = spring.getBean("flexibleSearchService");
def query = "SELECT {o.pk}  FROM { Order AS o }  WHERE { o.code } IN ( 'A06808673','A06808762','A06808832','A06808926','A06808943','A06809661','A06809804','A06809987','A06810057','A06810065','A06810384','A06810443','A06810596','A06810618','A06810644','A06810900','A06810921','A06810935','A06810945','A06810954','A06812535','A06812697','A06812756','A06812767','A06812778','A06812829','A06812830','A06812836','A06812889','A06812899','A06812914','A06812929','A06812950','A06812969','A06812987','A06812997','A06823134','A06823278','A06823295','A06823324','A06823342','A06823387','A06823407','A06823421','A06823455','A06823557','A06823817','A06823850','A06823857','A06823869','A06823945','A06823956','A06823974','A06823991','A06826047','A06826058','A06826287','A06826317','A06826365','A06826377','A06826453','A06826544','A06826549','A06826692','A06826713','A06826791','A06826853','A06826997','A06827029','A06827085','A06827143','A06827170','A06827381','A06827455','A06827510','A06827544','A06827658','A06827670','A06827681','A06827691','A06827778','A06827790','A06827880','A06827920','A06828067','A06828075','A06828121','A06828122','A06828221','A06828442','A06828465','A06828517','A06828530','A06828543','A06828579','A06828655','A06828659','A06828786','A06828848','A06828945','A06836036','A06836063','A06836243','A06836283','A06836288','A06836595','A06836732','A06836737','A06836877','A06837191','A06837204','A06837231','A06837279','A06837325','A06837477','A06837549','A06837598','A06837618','A06837699','A06837744','A06837849','A06838021','A06838252','A06838374','A06838425','A06838476','A06838530','A06838610','A06838783','A06838961','A06839105','A06839172','A06839205','A06839645','A06839698','A06839819','A06846072','A06846124','A06846286','A06846390','A06846559','A06846673','A06848183','A06848254','A06848343','A06848395','A06848579','A06848594','A06849124','A06849489','A06849548','A06849557','A06849617','A06851112','A06851122','A06851257','A06851366','A06851392','A06851397','A06851420','A06857025','A06857030','A06857043','A06858038','A06859090','A06860024','A06860142','E06828333','E06846085','V06826187','V06828046','V06828482','V06837148','V06837571','V06838820','V06848489','V06860070')"
def result = flexibleSearchService.search(query);
def alreadyChecked = [];

for ( order in result.getResult() ){
   
   if(!alreadyChecked.contains(order.getCode())){
    
    alreadyChecked.add(order.getCode());

    def customer = order.getUser();
    for( currentAccount in customer.getCurrentAccountList()){
      if (currentAccount.getType().equals(CurrentAccountType.CREDIT)){
            println order.getCode()+";"+customer.getUid()+";"+currentAccount.getAmount()+";"+currentAccount.getOtherReasons()
        }
    }
    
   }
}