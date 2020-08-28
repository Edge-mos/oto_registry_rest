package ru.autoins.oto_registry_rest.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.annotation.PostConstruct;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RegistryProvider implements DbAccess{

    private final JdbcTemplate template;

    private SimpleJdbcCall simpleJdbcCall;

    @Value("${procedure.schemaname}")
    private String schemaName;

    @Value("${procedure.name}")
    private String procedureName;

    @Autowired
    public RegistryProvider(JdbcTemplate template) {
        this.template = template;
    }

    @PostConstruct
    private void init() {
        this.template.setResultsMapCaseInsensitive(true);
        this.simpleJdbcCall = new SimpleJdbcCall(this.template)
                .withSchemaName(this.schemaName)
                .withProcedureName(this.procedureName);
    }


    public byte[] getRegistryBytes() {
        return this.simpleJdbcCall
                .execute()
                .values()
                .stream()
                .map(o -> o.toString().getBytes())
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public String getXmlStringFromDB() {     // тут будет параметр
        final Map<String, Object> execute = this.simpleJdbcCall.execute();

        final ArrayList<Object> innerWrapper = (ArrayList<Object>) execute.get(execute.keySet().toArray()[0]);
        final LinkedCaseInsensitiveMap<Object> wrapper = (LinkedCaseInsensitiveMap) innerWrapper.get(0);
        return Objects.requireNonNull(wrapper.get(wrapper.keySet().toArray()[0])).toString();
    }
}
