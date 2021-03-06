package fixtures.lroparameterizedendpoints.implementation;

import com.azure.core.util.CoreUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class Utils {
    static String getValueFromIdByName(String id, String name) {
        if (id == null) {
            return null;
        }
        Iterator<String> itr = Arrays.stream(id.split("/")).iterator();
        while (itr.hasNext()) {
            String part = itr.next();
            if (part != null && !part.trim().isEmpty()) {
                if (part.equalsIgnoreCase(name)) {
                    if (itr.hasNext()) {
                        return itr.next();
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    static String getValueFromIdByParameterName(String id, String pathTemplate, String parameterName) {
        if (id == null || pathTemplate == null) {
            return null;
        }
        String parameterNameParentheses = "{" + parameterName + "}";
        List<String> idSegmentsReverted = Arrays.asList(id.split("/"));
        List<String> pathSegments = Arrays.asList(pathTemplate.split("/"));
        Collections.reverse(idSegmentsReverted);
        Iterator<String> idItrReverted = idSegmentsReverted.iterator();
        int pathIndex = pathSegments.size();
        while (idItrReverted.hasNext() && pathIndex > 0) {
            String idSegment = idItrReverted.next();
            String pathSegment = pathSegments.get(--pathIndex);
            if (!CoreUtils.isNullOrEmpty(idSegment) && !CoreUtils.isNullOrEmpty(pathSegment)) {
                if (pathSegment.equalsIgnoreCase(parameterNameParentheses)) {
                    if (pathIndex == 0 || (pathIndex == 1 && pathSegments.get(0).isEmpty())) {
                        List<String> segments = new ArrayList<>();
                        segments.add(idSegment);
                        idItrReverted.forEachRemaining(segments::add);
                        Collections.reverse(segments);
                        if (segments.size() > 0 && segments.get(0).isEmpty()) {
                            segments.remove(0);
                        }
                        return String.join("/", segments);
                    } else {
                        return idSegment;
                    }
                }
            }
        }
        return null;
    }
}
