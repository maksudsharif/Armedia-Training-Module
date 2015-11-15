package com.someco.action.executer;

import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.repository.NodeRef;

public class EnableReviewable extends SetReviewable {
	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		action.setParameterValue(SetReviewable.PARAM_FLAG, true);
		super.executeImpl(action, actionedUponNodeRef);
	}
}
