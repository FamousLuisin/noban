import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Menu } from "lucide-react"
import { Link } from "react-router"

export default function AuthDropdown() {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger
        render={<Button variant="outline" size="icon" aria-label="Abrir menu da conta" />}
      >
        <Menu />
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuItem render={<Link to="/login" />}>Login</DropdownMenuItem>
        <DropdownMenuItem render={<Link to="/register" />}>Register</DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
