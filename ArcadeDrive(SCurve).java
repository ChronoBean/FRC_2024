public class ArcadeDrive(SCurve) extends TimedRobot {
  private final PWMSparkMax m_leftMotor = new PWMSparkMax(0);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(1);
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
  private final Joystick m_stick = new Joystick(0);

  private double currentSpeed = 0.0;

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    // Get joystick inputs
    double stickY = -m_stick.getY();
    double stickX = -m_stick.getX();

    // Set maximum change in speed per iteration
    double maxChange = 0.02;

    // Acceleration
    if (stickY > currentSpeed) {
      currentSpeed = Math.min(currentSpeed + maxChange, stickY);
    } else {
      currentSpeed = Math.max(currentSpeed - maxChange, stickY);
    }

    // Update motor speeds with arcade drive
    m_robotDrive.arcadeDrive(currentSpeed, stickX);
  }
}
