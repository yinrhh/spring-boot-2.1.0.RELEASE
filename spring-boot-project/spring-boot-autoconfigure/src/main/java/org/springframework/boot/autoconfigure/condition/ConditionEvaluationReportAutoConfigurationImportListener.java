/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.condition;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;

/**
 * {@link AutoConfigurationImportListener} to record results with the
 * {@link ConditionEvaluationReport}.
 *
 * @author Phillip Webb
 */
class ConditionEvaluationReportAutoConfigurationImportListener
		implements AutoConfigurationImportListener, BeanFactoryAware {

	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent event) {
		if (this.beanFactory != null) {
			// 获取到条件评估报告器对象
			ConditionEvaluationReport report = ConditionEvaluationReport
					.get(this.beanFactory);
			// 将符合条件的自动配置类记录到 unConditionalClass 集合中。
			report.recordEvaluationCandidates(event.getCandidateConfigurations());
			//将要 exclude 的自动配置类记录到 exclusions 集合中。
			report.recordExclusions(event.getExclusions());
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (beanFactory instanceof ConfigurableListableBeanFactory)
				? (ConfigurableListableBeanFactory) beanFactory : null;
	}

}
