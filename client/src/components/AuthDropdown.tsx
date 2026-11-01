import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { User } from "lucide-react"
import { Link } from "react-router"
import { useAuth } from "@/context/AuthContext"

export default function AuthDropdown() {
  const { name, logout, isLoggedIn } = useAuth()

  return (
    <DropdownMenu>
      <DropdownMenuTrigger
        render={<Button variant="default" aria-label="Abrir menu da conta" className="cursor-pointer flex gap-2 px-4 items-center" />}
      >
        <User /> {isLoggedIn() && <span className="max-[400px]:hidden">{name}</span>}
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuGroup>
          {isLoggedIn() ? (
            <>
              <DropdownMenuLabel>my account</DropdownMenuLabel>
              <DropdownMenuItem className="cursor-pointer">Dashboard</DropdownMenuItem>
              <DropdownMenuItem className="cursor-pointer" onClick={logout}>Logout</DropdownMenuItem>
            </>
          ) : (
            <>
              <DropdownMenuItem className="cursor-pointer" render={<Link to="/login" />}>Login</DropdownMenuItem>
              <DropdownMenuItem className="cursor-pointer" render={<Link to="/register" />}>Register</DropdownMenuItem>
            </>
          )}
        </DropdownMenuGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
