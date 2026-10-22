-- 1. Crear la Base de Datos
CREATE DATABASE IF NOT EXISTS musica_db;
USE musica_db;

-- 2. Tabla de Géneros Musicales
CREATE TABLE generos (
                         id_genero INT AUTO_INCREMENT PRIMARY KEY,
                         nombre_genero VARCHAR(50) NOT NULL UNIQUE,
                         descripcion TEXT
);

-- 3. Tabla de Artistas
CREATE TABLE artistas (
                          id_artista INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_artistico VARCHAR(100) NOT NULL,
                          pais_origen VARCHAR(50),
                          anio_formacion INT,
                          biografia TEXT
);

-- 4. Tabla de Álbumes
CREATE TABLE albumes (
                         id_album INT AUTO_INCREMENT PRIMARY KEY,
                         titulo VARCHAR(100) NOT NULL,
                         id_artista INT NOT NULL,
                         fecha_lanzamiento DATE,
                         portada_url VARCHAR(255),
                         CONSTRAINT fk_artista_album FOREIGN KEY (id_artista)
                             REFERENCES artistas(id_artista)
                             ON DELETE CASCADE
                             ON UPDATE CASCADE
);

-- 5. Tabla de Canciones
CREATE TABLE canciones (
                           id_cancion INT AUTO_INCREMENT PRIMARY KEY,
                           titulo VARCHAR(100) NOT NULL,
                           duracion_segundos INT NOT NULL CHECK (duracion_segundos > 0),
                           id_album INT NOT NULL,
                           id_genero INT NOT NULL,
                           numero_reproducciones BIGINT DEFAULT 0,
                           CONSTRAINT fk_album_cancion FOREIGN KEY (id_album)
                               REFERENCES albumes(id_album)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE,
                           CONSTRAINT fk_genero_cancion FOREIGN KEY (id_genero)
                               REFERENCES generos(id_genero)
                               ON DELETE RESTRICT
                               ON UPDATE CASCADE
);

-- 6. Tabla de Usuarios
CREATE TABLE usuarios (
                          id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
                          correo VARCHAR(100) NOT NULL UNIQUE,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          es_premium BOOLEAN DEFAULT FALSE
);