package me.magicsu.compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import me.magicsu.annotation.BindView;
import me.magicsu.annotation.OnClick;

@AutoService(Processor.class)
public class OneBindingProcessor extends AbstractProcessor {

    private Elements mElements;
    private Filer mFiler;
    private final Map<String, BoundClass> mBoundClassMap = new LinkedHashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mBoundClassMap.clear();

        try {
            findAndParseAnnotation(roundEnv);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true;
        }

        try {
            for (BoundClass annotatedClass : mBoundClassMap.values()) {
                info("Generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(mFiler);
            }
        } catch (IOException e) {
            error("Generate file failed, reason: %s", e.getMessage());
        }
        return true;
    }

    private void findAndParseAnnotation(RoundEnvironment roundEnv) {
        processBindView(roundEnv);
        processOnClick(roundEnv);
    }

    private void processBindView(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            BoundClass annotatedClass = getBoundClass(element);
            BindingViewField field = new BindingViewField(element);
            annotatedClass.addField(field);
        }
    }

    private void processOnClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)) {
            BoundClass annotatedClass = getBoundClass(element);
            BindingOnClickMethod method = new BindingOnClickMethod(element);
            annotatedClass.addMethod(method);
        }
    }

    private BoundClass getBoundClass(Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = enclosingElement.getQualifiedName().toString();
        BoundClass annotatedClass = mBoundClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new BoundClass(enclosingElement, mElements);
            mBoundClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
