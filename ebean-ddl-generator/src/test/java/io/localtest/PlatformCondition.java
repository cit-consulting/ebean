package io.localtest;

import io.ebean.DB;
import io.localtest.annotation.ForPlatform;
import io.ebean.annotation.Platform;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

public class PlatformCondition implements ExecutionCondition {

  private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled("ForPlatform is not present");

  @Override
  public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
    Optional<AnnotatedElement> element = context.getElement();
    if (element.isPresent()) {
      final ForPlatform annotation = element.get().getAnnotation(ForPlatform.class);
      if (annotation != null && !platformMath(annotation.value())) {
        return ConditionEvaluationResult.disabled("@ForPlatform");
      }
    }
    return ENABLED;
  }

  private boolean platformMath(Platform[] platforms) {
    Platform basePlatform = DB.getDefault().platform().base();
    for (Platform platform : platforms) {
      if (platform.equals(basePlatform)) {
        return true;
      }
    }
    return false;
  }
}
