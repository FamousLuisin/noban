import { Moon, Sun } from "lucide-react"
 
import { Button } from "@/components/ui/button"
import { useTheme } from "@/context/ThemeContext"
 
export function ModeToggle() {
  const { theme, setTheme } = useTheme()

  const onCLick = () => {
      if(theme === "dark") setTheme("light")
      else setTheme("dark")
  }
 
  return (
      <Button variant="outline" size="icon" className="cursor-pointer" onClick={onCLick}>
        <Sun className="h-[1.2rem] w-[1.2rem] scale-100 rotate-0 transition-all dark:scale-0 dark:-rotate-90" />
        <Moon className="absolute h-[1.2rem] w-[1.2rem] scale-0 rotate-90 transition-all dark:scale-100 dark:rotate-0" />
      </Button>
  )
}