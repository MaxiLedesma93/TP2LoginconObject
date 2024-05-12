package com.example.loginconobject.Registro;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.loginconobject.models.Usuario;

import java.io.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RegistroViewModel  extends AndroidViewModel {
    private MutableLiveData<Usuario> mUsuario;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public void recuperarUsuario() {
        File archivo = new File(getApplication().getFilesDir(), "usuario.dat");
        Usuario usu = new Usuario();

        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            try {
                usu = (Usuario) ois.readObject();
                String nombre = usu.getNombre();
                String apellido = usu.getApellido();
                String mail = usu.getEmail();
                String pass = usu.getPassword();
                long dni = usu.getDni();
                getMUsuario().setValue(usu);
                fis.close();
            } catch (EOFException eof) {
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
        }
    }

    public void guardarUsuario(String dni, String nombre, String apellido, String email, String password) {
        Usuario usu = new Usuario(Long.parseLong(dni),nombre, apellido, email, password);
        File archivo = new File(getApplication().getFilesDir(), "usuario.dat");
            try {
                if(archivo.length()!=0){
                    archivo.delete();
                }
                FileOutputStream fos = new FileOutputStream(archivo, false);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(usu);
                bos.flush();
                fos.close();
                usu = null;
                Toast.makeText(getApplication(), "Usuario Guardado", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
            }
    }
}



