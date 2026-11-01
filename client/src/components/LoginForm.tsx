import { loginSchema } from "@/schemas/LoginSchema"
import { useForm } from "@tanstack/react-form"
import { Field, FieldError, FieldGroup, FieldLabel } from "./ui/field"
import { Input } from "./ui/input"
import { InputGroup, InputGroupAddon, InputGroupButton, InputGroupInput } from "./ui/input-group"
import { useState } from "react"
import { EyeIcon, EyeOffIcon } from "lucide-react"
import { Button } from "./ui/button"
import type { LoginDTO } from "@/types/AuthType"
import { useAuth } from "@/context/AuthContext"
import { useNavigate } from "react-router"

export default function LoginForm(){
    const [showPassword, setShowPassword] = useState<boolean>(false)
    const loginMutation  = useAuth().authLogin()
    const navigate = useNavigate()
    
    const form = useForm({
        defaultValues: {
            email: "",
            password: ""
        } as LoginDTO,
        validators: {
            onSubmit: loginSchema
        },
        onSubmit: async({ value }) => {    
            await loginMutation.mutate(value, {
                onSuccess: () => navigate("/")
            })
        }
    })
    
    return (
        <form 
            id="login-form"
            onSubmit={(e) => {
                e.preventDefault()
                form.handleSubmit()
            }}
            className="flex flex-col gap-4"
        >
            <FieldGroup>
                <form.Field
                    name="email"
                    children={(field) => {
                        const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid
                        
                        return (
                            <Field data-invalid={isInvalid}>
                                <FieldLabel htmlFor={field.name}>
                                    Email<span className="text-destructive">*</span>
                                </FieldLabel>
                                <Input 
                                    id={field.name} 
                                    name={field.name}
                                    value={field.state.value}
                                    onBlur={field.handleBlur}
                                    onChange={(e) => field.handleChange(e.target.value)}
                                    aria-invalid={isInvalid}
                                    placeholder="digit your email here..."
                                    autoComplete="off"
                                    required
                                />
                                {isInvalid && (
                                    <FieldError errors={field.state.meta.errors} />
                                )}
                            </Field>
                        )
                    }}
                />
                <form.Field
                    name="password"
                    children={(field) => {
                        const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid
                        
                        return (
                            <Field data-invalid={isInvalid}>
                                <FieldLabel htmlFor={field.name}>
                                    Password<span className="text-destructive">*</span>
                                </FieldLabel>
                                <InputGroup>
                                    <InputGroupInput
                                        id={field.name} 
                                        name={field.name}
                                        value={field.state.value}
                                        onBlur={field.handleBlur}
                                        onChange={(e) => field.handleChange(e.target.value)}
                                        aria-invalid={isInvalid}
                                        placeholder="digit your password here..."
                                        autoComplete="off"
                                        type= {showPassword ? "text" : "password"}
                                        required
                                    />
                                    <InputGroupAddon align="inline-end">
                                        <InputGroupButton
                                            type="button"
                                            aria-label={showPassword ? "Hide password" : "Show password"}
                                            onClick={() => setShowPassword(!showPassword)}
                                            size="icon-xs"
                                        >
                                            {showPassword ? (
                                            <EyeOffIcon aria-hidden="true" />
                                            ) : (
                                            <EyeIcon aria-hidden="true" />
                                            )}
                                        </InputGroupButton>
                                    </InputGroupAddon>
                                </InputGroup>
                                
                                {isInvalid && (
                                    <FieldError errors={field.state.meta.errors} />
                                )}
                            </Field>
                        )
                    }}
                />
                <div className="flex justify-end">
                    <Field className="w-1/4 ">
                        <Button type="submit" form="login-form" className="cursor-pointer" disabled={loginMutation.isPending}>
                            {loginMutation.isPending ? "Entrando..." : "Submit"}
                        </Button>
                    </Field>
                </div>
            </FieldGroup>
            {loginMutation.isError && (
                <div className="border-2 border-destructive rounded-3xl bg-destructive/10">
                    <p className="p-3 text-destructive">
                        Não foi possível entrar. Confira suas credenciais e tente novamente.
                    </p>
                </div>
            )}
        </form>
    )
}
