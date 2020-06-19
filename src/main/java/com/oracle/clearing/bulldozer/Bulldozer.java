package com.oracle.clearing.bulldozer;

import com.oracle.clearing.site.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bulldozer {

    @Autowired
    private Site site;


}
