package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(User user) {
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserPorUsername(String username) {
        return userRepository.findByNombre(username); // devuelve Optional
    }

    @Transactional(readOnly = true)
    public User getUserPorUsernameYPassword(String username, String rawPassword) {
        User user = userRepository.findByNombre(username);
        if (user == null) {
            return null;
        }
        return passwordEncoder.matches(rawPassword, user.getPassword()) ? user : null;
    }

    @Transactional(readOnly = true)
    public User getUserPorUsernameOEmail(String username, String email) {
        return userRepository.findByNombreOrEmail(username, email); // Optional
    }

    @Transactional(readOnly = true)
    public boolean existeUserPorUsernameOEmail(String username, String email) {
        return userRepository.existsByNombreOrEmail(username, email);
    }

    @Transactional
    public boolean delete(User user) {
        try {
            userRepository.delete(user);
            userRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public User registrarNewUser(String nombre, String email, String rawPassword) {
        User nuevo = new User();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setPassword(passwordEncoder.encode(rawPassword));

        // arreglos clave
        nuevo.setRol("CLIENTE"); // o el que uses por defecto
        nuevo.setFecha_registro(LocalDateTime.now());

        User guardado = userRepository.save(nuevo);

        String asunto = "🐾 Bienvenido a Pawtify, " + nombre + "!";
        String loginUrl = "http://localhost:8080/login";

        String html = """
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="color-scheme" content="light dark">
  <meta name="supported-color-schemes" content="light dark">
  <title>Bienvenido</title>
</head>
<body style="margin:0; padding:0; background-color:#eef2f7;">
  <!-- Preheader -->
  <div style="display:none; max-height:0px; overflow:hidden; font-size:1px; line-height:1px; color:#eef2f7;">
    Tu cuenta en Pawtify está lista. Empieza ahora.
  </div>

  <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color:#eef2f7;">
    <tr>
      <td align="center" style="padding:32px 12px;">
        <table role="presentation" cellpadding="0" cellspacing="0" width="600" style="width:600px; max-width:600px; background:#ffffff; border-radius:12px; box-shadow:0 6px 24px rgba(0,0,0,0.08);">
          
          <!-- Header -->
          <tr>
            <td align="center" style="padding:28px 24px; background:#0ea5e9;">
              <div style="font-size:26px; font-weight:800; color:#ffffff; font-family:Segoe UI, Roboto, Arial, sans-serif; letter-spacing:0.2px;">
                <span style="font-size:28px;">🐾</span> Pawtify
              </div>
              <div style="font-size:14px; color:#dff6ff; font-family:Segoe UI, Roboto, Arial, sans-serif; margin-top:4px;">
                Bienvenido a la familia
              </div>
            </td>
          </tr>

          <!-- Body -->
          <tr>
            <td style="padding:32px 40px; font-family:Segoe UI, Roboto, Arial, sans-serif; color:#0f172a;">
              <h1 style="margin:0 0 12px 0; font-size:22px; line-height:1.3; font-weight:700;">Hola %s,</h1>
              <p style="margin:0 0 16px 0; font-size:15px; line-height:1.7;">
                Tu cuenta en <strong>Pawtify</strong> fue creada con éxito. Ya puedes explorar el panel y comenzar.
              </p>

              <!-- Botón -->
              <table role="presentation" cellspacing="0" cellpadding="0" style="margin:24px 0;">
                <tr>
                  <td align="center" bgcolor="#1d4ed8" style="border-radius:10px;">
                    <a href="%s" style="display:inline-block; padding:14px 26px; font-size:15px; font-weight:700; color:#ffffff; text-decoration:none; border-radius:10px; font-family:Segoe UI, Roboto, Arial, sans-serif;">
                      Iniciar sesión
                    </a>
                  </td>
                </tr>
              </table>

              <!-- Fallback link -->
              <p style="margin:0 0 4px 0; font-size:13px; color:#475569;">
                Si el botón no funciona, copia y pega este enlace:
              </p>
              <p style="margin:0 0 16px 0; font-size:13px; color:#1d4ed8; word-break:break-all;">%s</p>

              <hr style="border:none; border-top:1px solid #e2e8f0; margin:24px 0;" />

              <p style="margin:0; font-size:12px; color:#64748b;">
                Si no realizaste este registro, ignora este correo.
              </p>
            </td>
          </tr>

          <!-- Footer -->
          <tr>
            <td align="center" style="padding:16px 20px; background:#f8fafc; border-top:1px solid #e2e8f0; font-family:Segoe UI, Roboto, Arial, sans-serif; color:#64748b; font-size:12px;">
              © %d Pawtify. Todos los derechos reservados.
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</body>
</html>
""".formatted(nombre, loginUrl, loginUrl, java.time.Year.now().getValue());

        try {
            mailService.enviarCorreoHtml(email, asunto, html);
        } catch (Exception ignore) {
        }

        return guardado;
    }

    @Transactional
    public User save(User user, boolean actualizar) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        User existente = userRepository.findById(user.getId()).orElse(null);
        if (existente != null) {
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(existente.getPassword());
            } else if (!user.getPassword().equals(existente.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        return userRepository.save(user);
    }
}
