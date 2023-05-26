type user = {
    name: string,
    dni: string,
    avatar: string,
    role: 'student' | 'teacher'
}

type asignaturas = ({
    name: string,
    acronimo: string,
    nota: number
} | {
    name: string,
    acronimo: string,
    student: (user & {nota: number, role: 'student'})[]
})[]

base64(user).aes256(base64(user))
