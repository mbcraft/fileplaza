/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package it.mbcraft.fileplaza.data.serialization.managers.config;

import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.models.config.ApplicationReview;
import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.XMLSerializer;
import java.io.File;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FeedbackManager extends AbstractModelManager<ApplicationReview> {

    public FeedbackManager(String prefix) {
        super(new DirObjectStorage(new File(prefix+StorageHelper.DEFAULT_CONFIG_PATH)));
    }

    @Override
    protected String getRootName() {
        return "applicationReview";
    }

    @Override
    public Class getDataClass() {
        return ApplicationReview.class;
    }

    @Override
    public XMLSerializer getSerializer() {
        XMLSerializer xs = new XMLSerializer();
        xs.alias(getRootName(), ApplicationReview.class);
        return xs;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public String getModelKey(ApplicationReview obj) {
        return "application_review.xml";
    }
 
}
