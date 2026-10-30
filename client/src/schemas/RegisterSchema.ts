import * as z from "zod"

const reg = new RegExp("^(?=.{8,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$")

export const registerSchema = z.object({
    name: z.string().min(2, "Name must be between 2 and 50 characters"),
    email: z.email(),
    password: z.string().regex(reg, "Invalid password")
})