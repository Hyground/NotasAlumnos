package CRUDs;

import POJOs.Docentes;
import POJOs.Grados;
import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Carlos
 */
public class CDocentes {

    // Método para listar todos los docentes activos 
    public static List<Docentes> listarDocentes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Docentes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.createAlias("grados", "g"); // Alias para la relación con Grados
            criteria.createAlias("secciones", "s"); // Alias para la relación con Secciones
            criteria.add(Restrictions.eq("borradoLogico", true));  // Solo listar docentes activos
            criteria.addOrder(Order.asc("nombreCompleto")); // Ordenar por nombre completo
            lista = criteria.list(); // Retornar una lista completa de Docentes
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear un nuevo docente
    public static boolean crearDocente(String nombreCompleto, String cui, String nombreUsuario, String contrasenia, String rol, int gradoId, int seccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un docente con el mismo nombre de usuario
            Criteria criteriaUsuario = session.createCriteria(Docentes.class);
            criteriaUsuario.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            Docentes docenteExistente = (Docentes) criteriaUsuario.uniqueResult();

            if (docenteExistente != null) {
                throw new RuntimeException("El nombre de usuario '" + nombreUsuario + "' ya está en uso.");
            }

            // Obtener el grado y la sección existentes por sus IDs
            Grados grado = (Grados) session.get(Grados.class, gradoId);
            Secciones seccion = (Secciones) session.get(Secciones.class, seccionId);

            if (grado == null) {
                throw new RuntimeException("El grado con ID " + gradoId + " no existe.");
            }
            if (seccion == null) {
                throw new RuntimeException("La sección con ID " + seccionId + " no existe.");
            }

            // Verificar si ya hay un docente asignado a la misma sección y grado
            Criteria criteriaAsignacion = session.createCriteria(Docentes.class);
            criteriaAsignacion.add(Restrictions.eq("grados", grado));
            criteriaAsignacion.add(Restrictions.eq("secciones", seccion));
            Docentes docenteAsignado = (Docentes) criteriaAsignacion.uniqueResult();

            if (docenteAsignado != null) {
                throw new RuntimeException("Ya existe un docente asignado al grado '" + grado.getNombreGrado() + "' y sección '" + seccion.getNombreSeccion() + "'.");
            }

            // Hashear la contraseña
            String hash = BCrypt.hashpw(contrasenia, BCrypt.gensalt());

            // Crear el nuevo docente
            Docentes docente = new Docentes();
            docente.setNombreCompleto(nombreCompleto);
            docente.setCui(cui);
            docente.setNombreUsuario(nombreUsuario);
            docente.setContrasenia(hash); // contraseña hasheada
            docente.setRol(rol);
            docente.setGrados(grado);
            docente.setSecciones(seccion);
            docente.setBorradoLogico(true);

            session.save(docente);
            flag = true;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // metodo para actualizar docente, 
    public static boolean actualizarDocente(int usuarioId, String cui, String nuevoNombreUsuario, String nuevaContrasenia, String nuevoRol, int nuevoGradoId, int nuevaSeccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null) {
                Grados nuevoGrado = (Grados) session.get(Grados.class, nuevoGradoId);
                Secciones nuevaSeccion = (Secciones) session.get(Secciones.class, nuevaSeccionId);

                if (nuevoGrado == null) {
                    throw new RuntimeException("El grado con ID " + nuevoGradoId + " no existe.");
                }
                if (nuevaSeccion == null) {
                    throw new RuntimeException("La sección con ID " + nuevaSeccionId + " no existe.");
                }

                Criteria criteriaAsignacion = session.createCriteria(Docentes.class);
                criteriaAsignacion.add(Restrictions.eq("grados", nuevoGrado));
                criteriaAsignacion.add(Restrictions.eq("secciones", nuevaSeccion));
                criteriaAsignacion.add(Restrictions.ne("usuarioId", usuarioId));
                Docentes docenteAsignado = (Docentes) criteriaAsignacion.uniqueResult();

                if (docenteAsignado != null) {
                    throw new RuntimeException("Ya existe un docente asignado al grado '"
                            + nuevoGrado.getNombreGrado() + "' y sección '" + nuevaSeccion.getNombreSeccion() + "'.");
                }

                docente.setCui(cui);
                docente.setNombreUsuario(nuevoNombreUsuario);
                docente.setRol(nuevoRol);
                docente.setGrados(nuevoGrado);
                docente.setSecciones(nuevaSeccion);

                // 🔐 Hashear y actualizar si se ingresó una nueva contraseña distinta
                if (nuevaContrasenia != null && !nuevaContrasenia.trim().isEmpty()) {
                    if (!BCrypt.checkpw(nuevaContrasenia, docente.getContrasenia())) {
                        String hashed = BCrypt.hashpw(nuevaContrasenia, BCrypt.gensalt());
                        docente.setContrasenia(hashed);
                    }
                }

                session.update(docente);
                flag = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return flag;
    }

    // Método para eliminar un docente
    public static boolean eliminarDocente(int usuarioId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente existente
            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null) {
                docente.setBorradoLogico(false);
                session.update(docente);
                flag = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para reactivar un docente 
    public static boolean reactivarDocente(int usuarioId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente existente
            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null && !docente.isBorradoLogico()) {
                docente.setBorradoLogico(true);
                session.update(docente);
                flag = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para obtener un docente por su ID
    public static Docentes obtenerDocentePorId(int usuarioId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Docentes docente = null;
        try {
            session.beginTransaction();
            docente = (Docentes) session.get(Docentes.class, usuarioId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return docente;
    }
    // Método para actualizar la contraseña mediante CUI y validando la contraseña antigua

    public static boolean actualizarContrasenia(String cui, String contraseniaAntigua, String nuevaContrasenia) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("cui", cui));
            List<Docentes> listaDocentes = criteria.list();

            if (listaDocentes.isEmpty()) {
                throw new RuntimeException("No existe un docente con el CUI: " + cui);
            }

            Docentes docenteCorrecto = null;
            for (Docentes docente : listaDocentes) {
                // 🔐 Comparar con BCrypt
                if (BCrypt.checkpw(contraseniaAntigua, docente.getContrasenia())) {
                    docenteCorrecto = docente;
                    break;
                }
            }

            if (docenteCorrecto == null) {
                throw new RuntimeException("La contraseña antigua no coincide.");
            }

            // 🔐 Hashear la nueva contraseña
            String hashNueva = BCrypt.hashpw(nuevaContrasenia, BCrypt.gensalt());
            docenteCorrecto.setContrasenia(hashNueva);

            session.update(docenteCorrecto);
            flag = true;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return flag;
    }

}
