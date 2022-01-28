/*
 * Copyright 2016 Adobe Systems Incorporated
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apps.we_retail_screens.components.product;

import java.io.StringWriter;
import java.util.Iterator;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceSession;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.commons.ImageResource;

public class ScreensProduct extends WCMUsePojo {

    /**
     * default logger
     */
    private static final Logger log = LoggerFactory.getLogger(ScreensProduct.class);

    @Override
    public void activate() throws Exception {
        getResponse().setCharacterEncoding("UTF-8");
    }

    public String getJSON() throws JSONException, CommerceException {
        StringWriter out = new StringWriter();
        JSONWriter w = new JSONWriter(out);
        Resource productResource = getCurrentPage().getContentResource("product");
        Product product = productResource == null ? null : productResource.adaptTo(Product.class);
        w.object();
        if (product != null) {

            // Backwards compatibility check to rewrite product path from /var/commerce to /etc/commerce in 6.3
            // getPIMProduct will return null if product cannot be resolved, which will be the case in 6.3
            if (product.getPIMProduct() == null) {
                productResource = new BackwardsCompatibilityResourceWrapper(productResource);
                product = productResource.adaptTo(Product.class);
                productResource.getValueMap();
            }

            ImageResource img = product.getImage();
            w.key("title").value(product.getTitle());
            w.key("description").value(product.getDescription());
            w.key("productPath").value(product.getPIMProduct().getPath());
            w.key("path").value(getCurrentPage().getPath());
            w.key("summary").value(product.getProperty("summary", String.class));
            w.key("features").value(product.getProperty("features", String.class));
            w.key("price").value(product.getProperty("price", Double.class));
            w.key("imageHref").value(img.getHref());
            w.key("variantAxes").array();
            String[] variantAxes = product.getProperty("cq:productVariantAxes", String[].class);
            if (variantAxes != null) {
                for (String variantAxe : variantAxes) {
                    w.value(variantAxe);
                }
            }
            w.endArray();
            w.key("variants").array();
            Iterator<String> variantAxesIterator;
            Product variant;
            String variantAxis;
            Iterator<Product> variantsIterator = product.getVariants();
            while (variantsIterator.hasNext()) {
                variant = variantsIterator.next();

                // Backwards compatibility check to rewrite variation path from /var/commerce to /etc/commerce in 6.3
                // getTitle will return null if product cannot be resolved, which will be the case in 6.3
                if (variant.getTitle() == null) {
                    Resource variantResource = variant.adaptTo(Resource.class);
                    variantResource = new BackwardsCompatibilityResourceWrapper(variantResource);
                    variant = variantResource.adaptTo(Product.class);
                }

                w.object();
                variantAxesIterator = product.getVariantAxes();
                while (variantAxesIterator.hasNext()) {
                    img = variant.getImage();
                    if (img != null) {
                        w.key("imageHref").value(img.getHref());
                    }
                    variantAxis = variantAxesIterator.next();
                    w.key("title").value(variant.getTitle());
                    w.key("path").value(variant.getPath());
                    w.key("basePath").value(getCurrentPage().getPath());
                    w.key(variantAxis).value(variant.getProperty(variantAxis, String.class));
                }
                w.endObject();
            }
            w.endArray();
        }
        w.endObject();
        return out.toString();
    }

    /**
     * A class to wrap product/variant resources or backwards compatibility reasons.
     * This will just rewrite all the product path references from /var/commerce
     * to /etc/commerce so the package is compatible with 6.3
     */
    private static class BackwardsCompatibilityResourceWrapper extends ResourceWrapper {

        ValueMap vm;
        BackwardsCompatibilityResourceWrapper(Resource baseResource) {
            super(baseResource);
            vm = baseResource.adaptTo(ModifiableValueMap.class);

            String productData = vm.get("productData", String.class);
            if (productData != null) {
                vm.put("productData", rewriteToCompatiblePath(productData));
            }

            String basePath = vm.get("basePath", String.class);
            if (basePath != null) {
                vm.put("basePath", rewriteToCompatiblePath(basePath));
            }

            String productMaster = vm.get("cq:productMaster", String.class);
            if (productMaster != null) {
                vm.put("cq:productMaster", rewriteToCompatiblePath(productMaster));
            }
        }

        private String rewriteToCompatiblePath(String path) {
            return path.replace("/var/commerce", "/etc/commerce");
        }

        @Override
        public ValueMap getValueMap() {
            return vm;
        }

    }

}
