/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.responses.PartStudiosGetFeaturesResponseFeatures;
import com.onshape.api.types.OnshapeDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class FeaturesTest {

    @Test
    public void test() throws OnshapeException {
        OnshapeDocument document = new OnshapeDocument("https://cad.onshape.com/documents/7d4da491917169170b09fba7/w/10faf5016d22c2d88c4721dd/e/9f3f6f723a3e1cf74c9c6904");
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }
        
        for(PartStudiosGetFeaturesResponseFeatures feature : o.partStudios().getFeatures().call(document).getFeatures()) {
            System.out.println(feature);
        }
        
        
    }
}
