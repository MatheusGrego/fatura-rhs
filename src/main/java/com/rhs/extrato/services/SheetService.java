package com.rhs.extrato.services;

import java.io.IOException;
import java.io.InputStream;

public interface SheetService {

    void insertSheet(InputStream file) throws IOException;

}
