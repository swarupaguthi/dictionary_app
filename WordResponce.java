package com.example.dictionaryapp.models;

import java.io.Serializable;
import java.util.List;

public class
WordResponse implements Serializable {
    public String word;
    public List<Phonetic> phonetics;
    public List<Meaning> meanings;
}
