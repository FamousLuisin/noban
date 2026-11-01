import { registerSchema } from "@/schemas/RegisterSchema"
import { useForm } from "@tanstack/react-form"
import { Field, FieldError, FieldGroup, FieldLabel } from "./ui/field"
import { Input } from "./ui/input"
import { InputGroup, InputGroupAddon, InputGroupButton, InputGroupInput } from "./ui/input-group"
import { useState } from "react"
import { EyeIcon, EyeOffIcon } from "lucide-react"
import { Button } from "./ui/button"
import type { RegisterDTO } from "@/types/AuthType"
import { useAuth } from "@/context/AuthContext"
import { useNavigate } from "react-router"

export default function RegisterForm(){
    const [showPassword, setShowPassword] = useState<Boolean>(false)
    const registerMutation  = useAuth().authRegister()
    const navigate = useNavigate()
    
    const form = useForm({
        defaultValues: {
            name: "",
            email: "",
            password: ""
        } as RegisterDTO,
        validators: {
            onSubmit: registerSchema
        },
        onSubmit: async({ value }) => {
            await registerMutation.mutate(value, {
                onSuccess: () => navigate("/")
            })
        }
    })
    
    return (
        <form 
            id="register-form"
            onSubmit={(e) => {
                e.preventDefault()
                form.handleSubmit()
            }}
        >
            <FieldGroup>
                <form.Field
                    name="name"
                    children={(field) => {
                        const isInvalid = field.state.meta.isTouched && !field.state.meta.isValid
                        
                        return (
                            <Field data-invalid={isInvalid}>
                                <FieldLabel htmlFor={field.name}>
                                    Name<span className="text-destructive">*</span>
                                </FieldLabel>
                                <Input 
                                    id={field.name} 
                                    name={field.name}
                                    value={field.state.value}
                                    onBlur={field.handleBlur}
                                    onChange={(e) => field.handleChange(e.target.value)}
                                    aria-invalid={isInvalid}
                                    placeholder="digit your name here..."
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
                                            aria-label={showPassword ? "Hide password" : "Show password"}
                                            onClick={() => setShowPassword(!showPassword)}
                                            size="icon-xs"
                                            type="button"
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
                    <Field className="w-1/4 "><Button type="submit" form="register-form" className="cursor-pointer">Submit</Button></Field>
                </div>
            </FieldGroup>
        </form>
    )
}