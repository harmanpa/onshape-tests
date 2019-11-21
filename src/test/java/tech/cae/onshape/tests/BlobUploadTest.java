/*
 * The MIT License
 *
 * Copyright 2019 CAE Tech Limited
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.cae.onshape.tests;

import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.responses.BlobElementsUploadFileCreateElementResponse;
import com.onshape.api.responses.DocumentsCreateDocumentResponse;
import com.onshape.api.types.Blob;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class BlobUploadTest {

    //@Test
    public void testOAuth() {
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }
        test(o);
    }

    @Test
    public void testAPIKeys() throws OnshapeException {
        Onshape o = new Onshape();
        o.setAPICredentials(System.getenv("ONSHAPE_API_ACCESSKEY"), System.getenv("ONSHAPE_API_SECRETKEY"));
        test(o);
    }

    public void test(Onshape o) {
        File f = new File(new File(System.getProperty("user.dir")), "/src/test/resources/stepexample.step");
        DocumentsCreateDocumentResponse createdDocument;
        try {
            createdDocument = o.documents().createDocument().isPublic(Boolean.TRUE).name("IAmATestDeleteMe").ownerType(0).call();
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to create document", ex);
            return;
        }
        BlobElementsUploadFileCreateElementResponse response;
        try {
            response = o.blobElements()
                    .uploadFileCreateElement()
                    .file(new Blob(f))
                    .translate(Boolean.TRUE)
                    .yAxisIsUp(Boolean.FALSE)
                    .flattenAssemblies(Boolean.FALSE)
                    .call(createdDocument.getId(), createdDocument.getDefaultWorkspace().getId());
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to upload blob to new element", ex);
        } catch (IOException ex) {
            Assertions.fail("Failed to read blob", ex);
        }
        try {
            o.documents().deleteDocument().call(createdDocument.getId());
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to delete document", ex);
        }
    }
}
