#!/bin/bash

# CentroEducativo v0.2 - Secuencia de órdenes (shell script)

# Iniciar sesión como administrador
echo "Iniciando sesión como administrador..."
./centroeducativo login -u admin -p adminpass

# Crear un nuevo curso
echo "Creando un nuevo curso..."
./centroeducativo create_course -n "Matemáticas" -d "Curso de matemáticas básicas" -l "L001" -t "Profesor A"

# Listar todos los cursos
echo "Listando todos los cursos..."
./centroeducativo list_courses

# Obtener información de un estudiante
echo "Obteniendo información de un estudiante..."
./centroeducativo get_student_info -id 12345

# Matricular un estudiante en un curso
echo "Matriculando un estudiante en un curso..."
./centroeducativo enroll_student -id 12345 -c "Matemáticas"

# Calificar a un estudiante en un curso
echo "Calificando a un estudiante en un curso..."
./centroeducativo grade_student -id 12345 -c "Matemáticas" -g 9.5

# Listar calificaciones de un estudiante
echo "Listando calificaciones de un estudiante..."
./centroeducativo list_grades -id 12345

# Cerrar sesión como administrador
echo "Cerrando sesión..."
./centroeducativo logout
