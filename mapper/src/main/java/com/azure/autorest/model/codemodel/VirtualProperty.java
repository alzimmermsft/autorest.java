package com.azure.autorest.model.codemodel;

import java.util.List;

public class VirtualProperty {
//    private Property property;
    private List<String> nameComponents;
    private List<String> nameOptions;
    private String name;
    private VirtualProperty accessViaProperty;
    private VirtualProperty accessViaMember;
    private Schema accessViaSchema;
    private Schema originalContainingSchema;
    private boolean isPrivate;
    private List<String> alias;
    private String description;
//    private PropertyFormat format;
    private boolean required;
}