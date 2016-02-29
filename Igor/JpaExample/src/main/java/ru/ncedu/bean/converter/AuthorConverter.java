package ru.ncedu.bean.converter;

import ru.ncedu.bean.Author;
import ru.ncedu.service.LibraryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Gamzat on 03.12.2015.
 */
@FacesConverter(forClass = Author.class, value = "AuthorConverter")
public class AuthorConverter implements Converter {
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return new Author(LibraryService.getAuthorById(Integer.parseInt(s)));
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null || !(o instanceof Author)) {
            return null;
        }
        return String.valueOf(((Author) o).getId());
    }
}
