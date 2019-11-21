/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.responses.DocumentsGetElementListResponseElements;
import com.onshape.api.types.OnshapeDocument;
import com.onshape.api.types.WVM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class FeatureScriptTest {

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
        String did = "12312312345abcabcabcdeff";
        String wid = o.documents().getDocument()
                .call(did).getDefaultWorkspace().getId();
        for (DocumentsGetElementListResponseElements element
                : o.documents().getElementList()
                        .call(did, WVM.Workspace, wid)
                        .getElements()) {
            if ("FEATURESTUDIO".equals(element.getElementType())) {
                System.out.println(element.getName());
                System.out.println(o.featureStudios().getFeatureStudioSpecs().call(did, WVM.Workspace, wid, element.getId()));
            }
        }
    }
}
