package io.searchbox.core;

import io.searchbox.AbstractAction;
import io.searchbox.Action;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dogukan Sonmez
 */


public class Validate extends AbstractAction implements Action {

    final static Logger log = LoggerFactory.getLogger(Validate.class);

    public static class Builder {
        private String index;
        private String type;
        private final Object query;

        public Builder(Object query) {
            this.query = query;
        }

        public Builder index(String val) {
            index = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Validate build() {
            return new Validate(this);
        }
    }

    private Validate(Builder builder) {
        super.indexName = builder.index;
        super.typeName = builder.type;
        setURI(buildURI(builder.index, builder.type, null));
        setData(builder.query);
    }

    protected String buildURI(String index, String type, String id) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(index)) sb.append(index);

        if (StringUtils.isNotBlank(type)) sb.append("/").append(type);

        if (StringUtils.isNotBlank(id)) sb.append("/").append(id);
        sb.append("/").append("_validate/query");

        log.debug("Created URI for validate action is :" + sb.toString());
        return sb.toString();
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    @Override
    public String getPathToResult() {
        return "valid";
    }
}
