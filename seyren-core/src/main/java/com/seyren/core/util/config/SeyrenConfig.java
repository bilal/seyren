/**
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
package com.seyren.core.util.config;

import static org.apache.commons.lang.StringUtils.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlTransient;

@Named
public class SeyrenConfig {

	private static final String DEFAULT_MONGO_URL = "mongodb://localhost:27017/seyren";
	public static final String DEFAULT_SMTP_HOST = "localhost";
	public static final String DEFAULT_SMTP_PORT = "25";
	
	private final GraphiteConfig graphite;
	private final String baseUrl;

	@Inject
	public SeyrenConfig(GraphiteConfig graphite) {
		this.graphite = graphite;
		this.baseUrl = stripEnd(environmentOrDefault("SEYREN_URL", "http://localhost:8080/seyren"), "/");
	}
	
	public GraphiteConfig getGraphite() {
		return graphite;
	}

    public String getBaseUrl() {
        return baseUrl;
    }

	public String getMongoUrl() {
		return getProperty("MONGO_URL", DEFAULT_MONGO_URL);
	}

	public String getSmtpHost() {
		return getProperty("SMTP_HOST", DEFAULT_SMTP_HOST);
	}

	public int getSmtpPort() {
		return Integer.parseInt(getProperty("SMTP_PORT", DEFAULT_SMTP_PORT));
	}

	public String getSmtpUsername() {
		return getProperty("SMTP_USERNAME", "");
	}

	public String getSmtpPassword() {
		return getProperty("SMTP_PASSWORD", "");
	}

	@XmlTransient
	public String getProperty(String propertyName, String defaultValue) {
		return environmentOrDefault(propertyName, defaultValue);
	}
    
    private static String environmentOrDefault(String propertyName, String defaultValue) {
        String value = System.getenv(propertyName);
        if (isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
	
}
