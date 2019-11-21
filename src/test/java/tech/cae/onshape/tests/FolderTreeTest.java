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
import com.onshape.api.responses.DocumentsGetVersionsResponseVersions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class FolderTreeTest {

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
        JsonNode treeNodes = o.call("GET", "/globaltreenodes/magic/1", null, o.buildMap(), o.buildMap(), JsonNode.class);
        System.out.println(treeNodes);
        
        DocumentsGetVersionsResponseVersions v = o.documents().getVersions().call("").getVersions()[0];
    }
}
