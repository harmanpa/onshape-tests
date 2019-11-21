/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class VersionsTest {

    @Test
    public void test() throws OnshapeException {
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }

        System.out.println(o.documents().getWorkspaces().call("9558507b2d8feaea012281be"));
        
//        System.out.println(o.call("GET", "https://cad.onshape.com/api/metadata/d/9558507b2d8feaea012281be/w/8a8c9fb7f12ace4bfb9f4dad?configuration=default", null, o.buildMap(),  o.buildMap(), JsonNode.class));
        
  //      System.out.println(o.metadata().getMetadata().call("9558507b2d8feaea012281be", WV.Workspace, "8a8c9fb7f12ace4bfb9f4dad"));
    }
}
