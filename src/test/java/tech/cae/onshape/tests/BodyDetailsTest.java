/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.responses.PartStudiosGetBodyDetailsResponseBodies;
import com.onshape.api.types.OnshapeDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class BodyDetailsTest {

    @Test
    public void test() throws OnshapeException {
        OnshapeDocument document = new OnshapeDocument("https://cad.onshape.com/documents/2104e2091052e34e5e6acc36/w/07e5b2a6bd21e25a5dc62956/e/9bcdf1e7dce10ce7d34d9546");
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }

        for (PartStudiosGetBodyDetailsResponseBodies body : o.partStudios().getBodyDetails().call(document).getBodies()) {
            System.out.println(body);
        }
    }
}
